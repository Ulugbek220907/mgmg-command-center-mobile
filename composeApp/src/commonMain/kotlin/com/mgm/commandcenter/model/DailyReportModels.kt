package com.mgm.commandcenter.model

enum class ReportStatus(val titleUz: String, val badgeColorHex: Long, val badgeTextColorHex: Long) {
    ACCEPTED("Qabul qilindi", 0xFFC8E8D0, 0xFF002110),
    AI_ENRICHED("AI bilan to‘ldirildi", 0xFFF0E8DB, 0xFF1E1A13),
    OVERDUE("Topshirilmadi", 0xFFF8E0A8, 0xFF221A05),
    PENDING("Kutilmoqda", 0xFFF8E0A8, 0xFF221A05)
}

data class DailyReportHistoryItem(
    val id: String,
    val dateLabel: String,
    val status: ReportStatus,
    val contentSnippet: String
)

data class DailyReport(
    val dateString: String = "24-may, 2024 (Asia/Tashkent)",
    val submissionWindowStart: String = "16:00",
    val submissionWindowEnd: String = "00:00",
    val reminderTime: String = "17:00",
    var textContent: String = "MGMG Command moduli bo‘yicha API integratsiyasi yakunlandi. B2B korporativ hamkorlar bilan 3 ta uchrashuv o‘tkazildi. To‘lov gateway testi muvaffaqiyatli yakunlandi.",
    var aiFollowUpQuestion: String = "“B2B mijozlar bilan tuzilgan shartnomalar bo‘yicha to‘lov sanalari aniqlandimi?”",
    var aiFollowUpAnswer: String = "Ha, 28-may sanasiga to‘lov tasdiqlandi",
    var isSubmitted: Boolean = false,
    var status: ReportStatus = ReportStatus.PENDING
) {
    val charCount: Int
        get() = textContent.length

    val isValidLength: Boolean
        get() = charCount >= 50
}
