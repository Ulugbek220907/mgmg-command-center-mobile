package com.mgm.commandcenter.model

enum class SopCategory(val titleUz: String, val iconName: String) {
    BUSINESS_TRIP("Xizmat safari", "commute"),
    VACATION_LEAVE("Ta'til / Ruxsat", "event_busy"),
    FINANCIAL_EXPENSE("Moddiy javobgarlik / Xarajat", "payments"),
    OTHER("Boshqa", "more_horiz")
}

enum class SopStatus(val titleUz: String, val badgeColorHex: Long) {
    PENDING("Kutilmoqda", 0xFFF8E0A8),
    APPROVED("Tasdiqlangan", 0xFFC8E8D0),
    CONDITIONAL("Shartli tasdiqlangan", 0xFFF0E8DB),
    NEED_INFO("Qo‘shimcha so‘ralgan", 0xFFE4E0D8),
    REJECTED("Rad etilgan", 0xFFFFDAD8)
}

enum class DirectorOutcome(val titleUz: String, val subtitleUz: String, val iconName: String) {
    APPROVE("Tasdiqlash", "To'liq ma'qullash", "check_circle"),
    CONDITIONAL("Shartli tasdiqlash", "Qo'shimcha talab bilan", "rule"),
    NEED_INFO("Qo'shimcha ma'lumot", "Izoh talab qilinadi", "help"),
    REJECT("Rad etish", "Bekor qilish asosi", "cancel")
}

data class DirectorDecision(
    val outcome: DirectorOutcome,
    val conditionOrInstructions: String,
    val decisionDate: String,
    val decisionTime: String,
    val signedByRole: String = "Operatsion Direktor",
    val digitalSignatureId: String = "884-OD"
)

data class SopPermissionRequest(
    val id: String = "EMJ-SOP-ADM-01",
    val trackingNumber: String = "PR-2024-089",
    val title: String = "B2B Xizmat safari ruxsatnomasi",
    val requesterName: String = "Alisher Po‘latov",
    val requesterRole: String = "B2B Sotuv mutaxassisi • EMJ-204",
    val requesterInitials: String = "AP",
    val category: SopCategory = SopCategory.BUSINESS_TRIP,
    val reasonText: String = "B2B mijozlarimiz (Samarqand mehmonxonalari tarmog‘i) bilan yangi shartnoma imzolash va yillik servis kelishuvini yakunlash uchun 2 kunlik xizmat safari zarur.",
    val cyrillicOfficialText: String = "«Самарқанд меҳмонхоналари тармоғи билан 2024-2025 йиллар учун умумий қиймати 450 млн сўмлик корпоратив кир ювиш ва сервис хизматлари шартномасини имзолаш мақсадида хизмат сафари ташкил этилмоқда.»",
    val datesRange: String = "27-may, 2024 (09:00) — 28-may, 2024 (18:00)",
    val substitutePerson: String = "Jamshid Saidov (Vazifani vaqtincha bajaruvchi)",
    val expenseLimitText: String = "1,800,000 UZS",
    val submissionDate: String = "24-may, 2024 • 14:30",
    val documentFilename: String = "EMJ-SOP-ADM-01_Alisher_Polatov.docx",
    val status: SopStatus = SopStatus.PENDING,
    val decision: DirectorDecision? = null
)
