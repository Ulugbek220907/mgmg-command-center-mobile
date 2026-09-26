package com.mgm.commandcenter.model

import kotlin.math.round

/**
 * 21 AI Agents System based on "ЭМЖИЕМ_AI_Агентлар_Тизими.docx"
 * and "ЭМЖИЕМ_AI_Агентлар_Трекери.xlsx"
 */

enum class UrgencyLevel(val titleUz: String, val minScore: Double) {
    CRITICAL("КРИТИК", 18.0),
    URGENT("ШОШИЛИНЧ", 12.0),
    NORMAL("ОДДИЙ", 4.8),
    UNNECESSARY("КЕРАКСИЗ", 0.0)
}

enum class AgentStatus(val titleUz: String) {
    NOT_CREATED("ЯРАТИЛМАГАН"),
    IN_PROGRESS("ҚУРИЛМОҚДА"),
    TESTING("ТЕСТДА"),
    ACTIVE("ИШЛАЯПТИ")
}

data class Agent(
    val number: Int,
    val code: String,
    val name: String,
    val block: String,
    val whatItDoes: String,
    val whyNeeded: String,
    val platform: String,
    val assignee: String,
    val status: AgentStatus,
    val moneyImpact: Int,          // 1 - 10
    val probability: Double,       // 0.1 - 1.0
    val bottleneckCoeff: Int,      // 1 - 3
    val buildHours: Int,
    val monthlySavedHours: Int,
    val maxAnnualProfit: Double,
    val monthlyCost: Double,
    val startDate: String = "",
    val endDate: String = ""
) {
    // Formula: Score = MoneyImpact * Probability * BottleneckCoeff
    val score: Double
        get() = (moneyImpact * probability * bottleneckCoeff * 10.0).let { round(it) / 10.0 }

    val urgency: UrgencyLevel
        get() = when {
            score >= UrgencyLevel.CRITICAL.minScore -> UrgencyLevel.CRITICAL
            score >= UrgencyLevel.URGENT.minScore -> UrgencyLevel.URGENT
            score >= UrgencyLevel.NORMAL.minScore -> UrgencyLevel.NORMAL
            else -> UrgencyLevel.UNNECESSARY
        }

    fun realAnnualProfit(realizationCoeff: Double = 0.5): Double =
        maxAnnualProfit * realizationCoeff

    val annualCost: Double
        get() = monthlyCost * 12.0

    fun calculateRoi(realizationCoeff: Double = 0.5): Double {
        if (annualCost <= 0.0) return -1.0
        val realProfit = realAnnualProfit(realizationCoeff)
        return (realProfit - annualCost) / annualCost
    }
}

data class AgentBlockSummary(
    val blockCode: String,
    val title: String,
    val agentCount: Int,
    val criticalCount: Int
)
