package com.mgm.commandcenter.model

data class UserProfile(
    val fullName: String = "Alisher Po'latov",
    val initials: String = "AP",
    val employeeCode: String = "EMJ-204",
    val roleTitle: String = "B2B Sotuv mutaxassisi",
    val companyDivision: String = "Primus Laundry",
    val assignedRegion: String = "Toshkent Markaz",
    val telegramUsername: String = "@alisher_mgmg",
    val connectedDevice: String = "Expo Push faol • iPhone 15 Pro",
    val isDailyReportPushEnabled: Boolean = true,
    val isReminderPushEnabled: Boolean = true,
    val isSopPushEnabled: Boolean = true,
    val appVersion: String = "v1.0.4 (Build 42)",
    val timezone: String = "Asia/Tashkent (UTC+5)",
    val inviteCode: String = "PRM-8492-TX09",
    val isBiometricsActive: Boolean = true
)
