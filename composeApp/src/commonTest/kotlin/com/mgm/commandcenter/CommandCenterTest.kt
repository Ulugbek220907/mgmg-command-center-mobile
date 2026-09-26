package com.mgm.commandcenter

import com.mgm.commandcenter.data.CommandCenterRepository
import com.mgm.commandcenter.data.MockDataProvider
import com.mgm.commandcenter.model.*
import kotlin.test.*

class CommandCenterTest {

    private lateinit var repository: CommandCenterRepository

    @BeforeTest
    fun setUp() {
        repository = CommandCenterRepository()
    }

    @Test
    fun testAgentScoreAndUrgencyFormula() {
        // Formula: Score = MoneyImpact * Probability * BottleneckCoeff
        val h0 = MockDataProvider.allAgents.find { it.code == "H0" }
        assertNotNull(h0)
        assertEquals(9, h0.moneyImpact)
        assertEquals(0.9, h0.probability)
        assertEquals(3, h0.bottleneckCoeff)
        assertEquals(24.3, h0.score)
        assertEquals(UrgencyLevel.CRITICAL, h0.urgency)

        val b4 = MockDataProvider.allAgents.find { it.code == "B4" }
        assertNotNull(b4)
        assertEquals(9, b4.moneyImpact)
        assertEquals(0.7, b4.probability)
        assertEquals(2, b4.bottleneckCoeff)
        assertEquals(12.6, b4.score)
        assertEquals(UrgencyLevel.URGENT, b4.urgency)

        val a4 = MockDataProvider.allAgents.find { it.code == "A4" }
        assertNotNull(a4)
        assertEquals(3, a4.moneyImpact)
        assertEquals(0.8, a4.probability)
        assertEquals(2, a4.bottleneckCoeff)
        assertEquals(4.8, a4.score)
        assertEquals(UrgencyLevel.NORMAL, a4.urgency)
    }

    @Test
    fun testFinancialCalculationsAndRoi() {
        val financial = FinancialHealthSummary()

        // Real profit with default 0.5 coeff
        assertEquals(74_000.0, financial.realAnnualGainUsd)
        assertEquals(7_080.0, financial.recommendedAnnualCostUsd)

        // Net gain: 74,000 - 7,080 = 66,920
        assertEquals(66_920.0, financial.netAnnualProfitImprovementUsd)

        // ROI multiplier: 66,920 / 7,080 ≈ 9.45x
        val roi = financial.roiMultiplier
        assertTrue(roi > 9.4 && roi < 9.5, "ROI should be ~9.45x")

        // Payback period: 7,080 / (74,000 / 12) ≈ 1.15 months
        val payback = financial.paybackMonths
        assertTrue(payback > 1.1 && payback < 1.2, "Payback should be ~1.15 months")
    }

    @Test
    fun testGoldenRuleMaxTwoConcurrentAgents() {
        // Initially 0 active agents
        assertEquals(0, repository.countActiveConcurrentAgents())

        // Start 1st agent -> should succeed
        val firstStarted = repository.updateAgentStatus("H0", AgentStatus.IN_PROGRESS)
        assertTrue(firstStarted)
        assertEquals(1, repository.countActiveConcurrentAgents())

        // Start 2nd agent -> should succeed
        val secondStarted = repository.updateAgentStatus("A1", AgentStatus.TESTING)
        assertTrue(secondStarted)
        assertEquals(2, repository.countActiveConcurrentAgents())

        // Start 3rd agent -> MUST FAIL by golden rule!
        val thirdStarted = repository.updateAgentStatus("B1", AgentStatus.IN_PROGRESS)
        assertFalse(thirdStarted, "Starting a 3rd concurrent agent must fail by Golden Rule")
        assertEquals(2, repository.countActiveConcurrentAgents())

        // Transition first to ACTIVE (done) -> slot opens up
        val firstCompleted = repository.updateAgentStatus("H0", AgentStatus.ACTIVE)
        assertTrue(firstCompleted)
        assertEquals(1, repository.countActiveConcurrentAgents())

        // Now starting 3rd agent should succeed
        val thirdRetry = repository.updateAgentStatus("B1", AgentStatus.IN_PROGRESS)
        assertTrue(thirdRetry, "Slot opened, starting 3rd agent should now succeed")
        assertEquals(2, repository.countActiveConcurrentAgents())
    }

    @Test
    fun testDailyReportSubmissionValidation() {
        // Less than 50 chars should be rejected
        val tooShort = "Bajarildi."
        val shortResult = repository.submitDailyReport(tooShort, "")
        assertFalse(shortResult, "Daily report under 50 characters must be rejected")

        // Valid length text
        val validText = "MGMG Command moduli bo‘yicha API integratsiyasi yakunlandi. B2B korporativ hamkorlar bilan uchrashuv o‘tkazildi."
        val validResult = repository.submitDailyReport(validText, "Ha, 28-may sanasiga to‘lov tasdiqlandi")
        assertTrue(validResult, "Valid daily report should be accepted")

        val currentReport = repository.dailyReport.value
        assertTrue(currentReport.isSubmitted)
        assertEquals(ReportStatus.AI_ENRICHED, currentReport.status)

        // Check history updated
        val history = repository.reportHistory.value
        assertTrue(history.isNotEmpty())
        assertEquals(ReportStatus.AI_ENRICHED, history.first().status)
    }

    @Test
    fun testCyrillicLegalConversion() {
        val latinText = "Samarqand shahriga yangi shartnoma imzolash uchun xizmat safari"
        val cyrillic = repository.convertToCyrillicLegal(latinText)

        assertTrue(cyrillic.contains("Самарқанд"), "Should convert Samarqand")
        assertTrue(cyrillic.contains("шаҳрига"), "Should convert shahriga")
        assertTrue(cyrillic.contains("янги"), "Should convert yangi")
        assertTrue(cyrillic.contains("шартнома"), "Should convert shartnoma")
        assertTrue(cyrillic.contains("хизмат"), "Should convert xizmat")
        assertTrue(cyrillic.contains("сафари"), "Should convert safari")
    }

    @Test
    fun testSopRequestLifecycleAndDirectorDecision() {
        val newSop = repository.createSopRequest(
            category = SopCategory.BUSINESS_TRIP,
            reason = "Buxoro distribyutorlik uchrashuvi va uskunalarni tekshirish",
            substitutePerson = "Jamshid Saidov",
            datesRange = "29-may - 30-may, 2024",
            expenseLimit = "1,200,000 UZS"
        )

        assertEquals(SopStatus.PENDING, newSop.status)
        assertTrue(newSop.id.startsWith("EMJ-SOP-ADM-"))

        // Director conditionally approves
        repository.recordDirectorDecision(
            outcome = DirectorOutcome.CONDITIONAL,
            conditionOrInstructions = "Xarajatlar limitdan oshmasin va qaytgach 3 kunda hisobot berilsin."
        )

        val updatedSop = repository.currentSopRequest.value
        assertEquals(SopStatus.CONDITIONAL, updatedSop.status)
        assertNotNull(updatedSop.decision)
        assertEquals(DirectorOutcome.CONDITIONAL, updatedSop.decision!!.outcome)
        assertEquals("884-OD", updatedSop.decision!!.digitalSignatureId)
    }

    @Test
    fun testExecutiveFiveNumbersAndSafetyMargin() {
        val fiveNumbers = ExecutiveFiveNumbers(
            cashAmountUzs = 184_200_000.0,
            yesterdaySalesUzs = 42_500_000.0,
            inventoryValueUsd = 486_733.0,
            breakEvenMonthlyUzs = 942_000_000.0,
            isStockRising = true,
            isCashDropping = true
        )

        // Both conditions met -> halt alert active
        assertTrue(fiveNumbers.isOrderHaltAlertActive)

        // Safety margin with 1,200,000,000 UZS monthly sales:
        // (1,200,000,000 - 942,000,000) / 1,200,000,000 = 258,000,000 / 1,200,000,000 = 0.215 (21.5%)
        val margin = fiveNumbers.calculateSafetyMargin(1_200_000_000.0)
        assertTrue(margin > 0.21 && margin < 0.22)
    }

    @Test
    fun testAuthenticationInviteCode() {
        assertFalse(repository.login("ABC"))
        assertTrue(repository.login("PRM-8492-TX09"))
        assertTrue(repository.isLoggedIn.value)

        repository.logout()
        assertFalse(repository.isLoggedIn.value)
    }

    @Test
    fun testIosSmartPunctuationApostrophesAndTransliteration() {
        // iOS smart punctuation right curly apostrophe ’ (\u2019)
        val iosCurlyO = "O’rinbosar xodim"
        val cyrillicCurlyO = repository.convertToCyrillicLegal(iosCurlyO)
        assertTrue(cyrillicCurlyO.contains("Ўринбосар"), "iOS curly apostrophe O’ should become Ў")

        val iosCurlyG = "g’olib bo‘lim"
        val cyrillicCurlyG = repository.convertToCyrillicLegal(iosCurlyG)
        assertTrue(cyrillicCurlyG.contains("ғолиб"), "iOS curly apostrophe g’ should become ғ")

        // Left curly quote ‘ (\u2018)
        val leftQuoteText = "O‘zbekiston Respublikasi"
        val cyrillicLeftQuote = repository.convertToCyrillicLegal(leftQuoteText)
        assertTrue(cyrillicLeftQuote.contains("Ўзбекистон"), "Left quote O‘ should become Ў")

        // Official turned comma ʻ (\u02BB)
        val turnedCommaText = "oʻgʻli va qizi"
        val cyrillicTurnedComma = repository.convertToCyrillicLegal(turnedCommaText)
        assertTrue(cyrillicTurnedComma.contains("ўғли"), "Turned comma oʻgʻli should become ўғли")

        // Modifier apostrophe ʼ (\u02BC)
        val modifierAposText = "gʼolib"
        val cyrillicModifierApos = repository.convertToCyrillicLegal(modifierAposText)
        assertTrue(cyrillicModifierApos.contains("ғолиб"), "Modifier apostrophe gʼ should become ғ")

        // Ye and Ts rules
        val yeText = "yevropa"
        val cyrillicYe = repository.convertToCyrillicLegal(yeText)
        assertTrue(cyrillicYe.contains("европа"), "ye should become е")
    }

    @Test
    fun testOrderHaltConditionsDynamicToggle() {
        // Initial state in repository: isStockRising = false, isCashDropping = false -> alert is inactive
        assertFalse(repository.executiveNumbers.value.isOrderHaltAlertActive, "Initially halt alert must be inactive")

        // Enable both conditions -> alert must activate
        repository.updateOrderHaltConditions(isStockRising = true, isCashDropping = true)
        assertTrue(repository.executiveNumbers.value.isOrderHaltAlertActive, "Alert must activate when stock rising and cash dropping")

        // When cash is NOT dropping, halt rule must NOT fire
        repository.updateOrderHaltConditions(isStockRising = true, isCashDropping = false)
        assertFalse(repository.executiveNumbers.value.isOrderHaltAlertActive, "Alert should be false if cash is not dropping")

        // When stock is NOT rising, halt rule must NOT fire
        repository.updateOrderHaltConditions(isStockRising = false, isCashDropping = true)
        assertFalse(repository.executiveNumbers.value.isOrderHaltAlertActive, "Alert should be false if stock is not rising")

        // When both are false, halt rule must NOT fire
        repository.updateOrderHaltConditions(isStockRising = false, isCashDropping = false)
        assertFalse(repository.executiveNumbers.value.isOrderHaltAlertActive, "Alert should be false when both conditions false")

        // Re-enable both -> alert must reactivate immediately
        repository.updateOrderHaltConditions(isStockRising = true, isCashDropping = true)
        assertTrue(repository.executiveNumbers.value.isOrderHaltAlertActive, "Alert must reactivate when both conditions true")
    }

    @Test
    fun testRealizationCoefficientDynamicUpdate() {
        // Default coeff 0.5
        assertEquals(0.5, repository.financialHealth.value.realizationCoeff)
        assertEquals(74_000.0, repository.financialHealth.value.realAnnualGainUsd)

        // Update to 0.8
        repository.setRealizationCoefficient(0.8)
        val updatedFin = repository.financialHealth.value
        assertEquals(0.8, updatedFin.realizationCoeff)
        assertEquals(118_400.0, updatedFin.realAnnualGainUsd)
        assertEquals(111_320.0, updatedFin.netAnnualProfitImprovementUsd)
        assertTrue(updatedFin.roiMultiplier > 15.7 && updatedFin.roiMultiplier < 15.8)

        // Reset to 0.5
        repository.setRealizationCoefficient(0.5)
        assertEquals(0.5, repository.financialHealth.value.realizationCoeff)
    }

    @Test
    fun testMultipleSopRequestsSequence() {
        // Initial default SOP
        val firstSop = repository.currentSopRequest.value
        assertEquals(SopStatus.PENDING, firstSop.status)

        // Approve first SOP
        repository.recordDirectorDecision(DirectorOutcome.APPROVE, "Ruxsat berildi")
        assertEquals(SopStatus.APPROVED, repository.currentSopRequest.value.status)
        assertNotNull(repository.currentSopRequest.value.decision)

        // Create new SOP -> must replace current, reset status to PENDING, decision to null
        val secondSop = repository.createSopRequest(
            category = SopCategory.FINANCIAL_EXPENSE,
            reason = "Yangi mahsulot uchun B2B reklama kampaniyasiga bannerlar buyurtma qilish",
            substitutePerson = "Alisher Qodirov",
            datesRange = "01-iyun - 05-iyun, 2024",
            expenseLimit = "5,000,000 UZS"
        )
        val current = repository.currentSopRequest.value
        assertEquals(secondSop.id, current.id)
        assertEquals(SopCategory.FINANCIAL_EXPENSE, current.category)
        assertEquals(SopStatus.PENDING, current.status)
        assertNull(current.decision, "New SOP must not carry over previous decision")
    }
}
