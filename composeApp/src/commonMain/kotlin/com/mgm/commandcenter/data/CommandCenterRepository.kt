package com.mgm.commandcenter.data

import com.mgm.commandcenter.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

class CommandCenterRepository {

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _agents = MutableStateFlow(MockDataProvider.allAgents)
    val agents: StateFlow<List<Agent>> = _agents.asStateFlow()

    private val _realizationCoeff = MutableStateFlow(0.5)
    val realizationCoeff: StateFlow<Double> = _realizationCoeff.asStateFlow()

    private val _executiveNumbers = MutableStateFlow(ExecutiveFiveNumbers())
    val executiveNumbers: StateFlow<ExecutiveFiveNumbers> = _executiveNumbers.asStateFlow()

    private val _financialHealth = MutableStateFlow(FinancialHealthSummary())
    val financialHealth: StateFlow<FinancialHealthSummary> = _financialHealth.asStateFlow()

    private val _dailyReport = MutableStateFlow(DailyReport())
    val dailyReport: StateFlow<DailyReport> = _dailyReport.asStateFlow()

    private val _reportHistory = MutableStateFlow(MockDataProvider.initialHistoryItems)
    val reportHistory: StateFlow<List<DailyReportHistoryItem>> = _reportHistory.asStateFlow()

    private val _currentSopRequest = MutableStateFlow(SopPermissionRequest())
    val currentSopRequest: StateFlow<SopPermissionRequest> = _currentSopRequest.asStateFlow()

    private val _sopRequests = MutableStateFlow(listOf(SopPermissionRequest()))
    val sopRequests: StateFlow<List<SopPermissionRequest>> = _sopRequests.asStateFlow()

    fun login(inviteCode: String): Boolean {
        val clean = inviteCode.trim().uppercase()
        if (clean.length >= 8) {
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun logout() {
        _isLoggedIn.value = false
    }

    fun setRealizationCoefficient(coeff: Double) {
        val clamped = coeff.coerceIn(0.1, 1.0)
        _realizationCoeff.value = clamped
        _financialHealth.update { it.copy(realizationCoeff = clamped) }
    }

    /**
     * Golden rule from docx section 10 and xlsx sheet 1:
     * "Бир вақтда фақат 2 та агент қурилади. Учинчиси биттаси ТЎЛИҚ ишлаб кетмагунча бошланмайди."
     */
    fun countActiveConcurrentAgents(): Int {
        return _agents.value.count {
            it.status == AgentStatus.IN_PROGRESS || it.status == AgentStatus.TESTING
        }
    }

    fun canStartNewAgent(agentCode: String): Boolean {
        val existing = _agents.value.find { it.code == agentCode } ?: return false
        if (existing.status == AgentStatus.IN_PROGRESS || existing.status == AgentStatus.TESTING) {
            return true
        }
        return countActiveConcurrentAgents() < 2
    }

    fun updateAgentStatus(code: String, newStatus: AgentStatus): Boolean {
        if ((newStatus == AgentStatus.IN_PROGRESS || newStatus == AgentStatus.TESTING) && !canStartNewAgent(code)) {
            return false // Golden rule constraint violation
        }
        _agents.update { list ->
            list.map {
                if (it.code == code) it.copy(status = newStatus) else it
            }
        }
        return true
    }

    fun submitDailyReport(summary: String, followUpAnswer: String): Boolean {
        if (summary.trim().length < 50) return false
        val newStatus = if (followUpAnswer.isNotBlank()) ReportStatus.AI_ENRICHED else ReportStatus.ACCEPTED
        _dailyReport.update {
            it.copy(
                textContent = summary,
                aiFollowUpAnswer = followUpAnswer,
                isSubmitted = true,
                status = newStatus
            )
        }
        val newHistoryItem = DailyReportHistoryItem(
            id = "rep-${Clock.System.now().toEpochMilliseconds()}",
            dateLabel = "Bugun — 17:45",
            status = newStatus,
            contentSnippet = summary.take(120) + if (summary.length > 120) "..." else ""
        )
        _reportHistory.update { listOf(newHistoryItem) + it }
        return true
    }

    fun createSopRequest(
        category: SopCategory,
        reason: String,
        substitutePerson: String,
        datesRange: String,
        expenseLimit: String
    ): SopPermissionRequest {
        val newId = "EMJ-SOP-ADM-0${_sopRequests.value.size + 1}"
        val newTrack = "PR-2024-${(100 + _sopRequests.value.size + 1)}"
        val cyrillic = convertToCyrillicLegal(reason)

        val newRequest = SopPermissionRequest(
            id = newId,
            trackingNumber = newTrack,
            title = "${category.titleUz} ruxsatnomasi",
            category = category,
            reasonText = reason,
            cyrillicOfficialText = "«$cyrillic»",
            datesRange = datesRange,
            substitutePerson = substitutePerson,
            expenseLimitText = expenseLimit,
            status = SopStatus.PENDING
        )

        _currentSopRequest.value = newRequest
        _sopRequests.update { listOf(newRequest) + it }
        return newRequest
    }

    fun recordDirectorDecision(outcome: DirectorOutcome, conditionOrInstructions: String) {
        val decision = DirectorDecision(
            outcome = outcome,
            conditionOrInstructions = conditionOrInstructions,
            decisionDate = "24-may, 2024",
            decisionTime = "14:45",
            signedByRole = "Operatsion Direktor",
            digitalSignatureId = "884-OD"
        )
        val newStatus = when (outcome) {
            DirectorOutcome.APPROVE -> SopStatus.APPROVED
            DirectorOutcome.CONDITIONAL -> SopStatus.CONDITIONAL
            DirectorOutcome.NEED_INFO -> SopStatus.NEED_INFO
            DirectorOutcome.REJECT -> SopStatus.REJECTED
        }
        _currentSopRequest.update {
            it.copy(
                status = newStatus,
                decision = decision
            )
        }
        _sopRequests.update { list ->
            list.map {
                if (it.id == _currentSopRequest.value.id) {
                    it.copy(status = newStatus, decision = decision)
                } else it
            }
        }
    }

    fun updateOrderHaltConditions(isStockRising: Boolean, isCashDropping: Boolean) {
        _executiveNumbers.update {
            it.copy(isStockRising = isStockRising, isCashDropping = isCashDropping)
        }
    }

    fun convertToCyrillicLegal(latinText: String): String {
        // Standardize all Uzbek apostrophe variants including iOS smart apostrophe (’),
        // left apostrophe (‘), official turned comma (ʻ), modifier apostrophe (ʼ), and grave accent (`)
        val normalized = latinText
            .replace('‘', '\'')
            .replace('’', '\'')
            .replace('ʻ', '\'')
            .replace('ʼ', '\'')
            .replace('`', '\'')

        val map = mapOf(
            "sh" to "ш", "Sh" to "Ш", "SH" to "Ш",
            "ch" to "ч", "Ch" to "Ч", "CH" to "Ч",
            "yo" to "ё", "Yo" to "Ё", "YO" to "Ё",
            "yu" to "ю", "Yu" to "Ю", "YU" to "Ю",
            "ya" to "я", "Ya" to "Я", "YA" to "Я",
            "ye" to "е", "Ye" to "Е", "YE" to "Е",
            "ts" to "ц", "Ts" to "Ц", "TS" to "Ц",
            "o'" to "ў", "O'" to "Ў",
            "g'" to "ғ", "G'" to "Ғ",
            "a" to "а", "A" to "А",
            "b" to "б", "B" to "Б",
            "d" to "д", "D" to "Д",
            "e" to "е", "E" to "Е",
            "f" to "ф", "F" to "Ф",
            "g" to "г", "G" to "Г",
            "h" to "ҳ", "H" to "Ҳ",
            "i" to "и", "I" to "И",
            "j" to "ж", "J" to "Ж",
            "k" to "к", "K" to "К",
            "l" to "л", "L" to "Л",
            "m" to "м", "M" to "М",
            "n" to "н", "N" to "Н",
            "o" to "о", "O" to "О",
            "p" to "п", "P" to "П",
            "q" to "қ", "Q" to "Қ",
            "r" to "р", "R" to "Р",
            "s" to "с", "S" to "С",
            "t" to "т", "T" to "Т",
            "u" to "у", "U" to "У",
            "v" to "в", "V" to "В",
            "x" to "х", "X" to "Х",
            "y" to "й", "Y" to "Й",
            "z" to "з", "Z" to "З"
        )
        var result = normalized
        // Replace multi-char combos first (sh, ch, yo, o', g', etc.)
        for ((k, v) in map.entries.sortedByDescending { it.key.length }) {
            result = result.replace(k, v)
        }
        return result
    }

    companion object {
        val instance by lazy { CommandCenterRepository() }
    }
}
