package com.mgm.commandcenter.model

/**
 * Executive and Financial Domain models based on
 * "ЭМЖИЕМ_AI_Агентлар_Тизими.docx" and "ЭМЖИЕМ_AI_Агентлар_Трекери.xlsx"
 */

data class ExecutiveFiveNumbers(
    val cashAmountUzs: Double = 184_200_000.0,
    val yesterdaySalesUzs: Double = 42_500_000.0,
    val inventoryValueUsd: Double = 486_733.0,
    val clientDebtUzs: Double = 215_800_000.0,
    val todayScheduledPaymentsUzs: Double = 38_100_000.0,
    val breakEvenMonthlyUzs: Double = 942_000_000.0,
    val isStockRising: Boolean = false,
    val isCashDropping: Boolean = false
) {
    // Audit rule from docx section 12:
    // "Агар (Захира ошди) ВА (Касса тушди) бўлса -> барча янги буюртма ТЎХТАЙДИ."
    val isOrderHaltAlertActive: Boolean
        get() = isStockRising && isCashDropping

    // Safety margin: (MonthlySales - BreakEven) / MonthlySales
    fun calculateSafetyMargin(estimatedMonthlySalesUzs: Double): Double {
        if (estimatedMonthlySalesUzs <= 0) return 0.0
        return (estimatedMonthlySalesUzs - breakEvenMonthlyUzs) / estimatedMonthlySalesUzs
    }
}

data class PlanStage(
    val stageNumber: Int,
    val stageName: String,
    val daysRange: String,
    val agentCodes: List<String>,
    val goalDescription: String,
    val workloadHours: Int,
    val statusUz: String = "ЯРАТИЛМАГАН",
    val qualityIRatio: Double = 0.0 // Quality I ratio (И >= 0.80 passes, < 0.50 halts)
)

data class UnnecessaryAgent(
    val code: String,
    val name: String,
    val whatWasPromised: String,
    val whyNotNeeded: String,
    val alternativeSolution: String,
    val score: Double,
    val conclusion: String = "ҚУРИЛМАСИН"
)

data class BudgetOption(
    val optionNumber: Int,
    val title: String,
    val monthlyCostUsd: Double,
    val annualCostUsd: Double,
    val opexPercent: Double,
    val agentCount: String,
    val implementer: String,
    val timeframe: String,
    val riskDescription: String,
    val isRecommended: Boolean = false
)

data class FinancialHealthSummary(
    val annualRunRateUsd: Double = 1_190_000.0,
    val annualNetProfitUsd: Double = 107_000.0,
    val monthlyOpexUsd: Double = 29_500.0,
    val employeeCount: Int = 22,
    val deadStock365DaysUsd: Double = 45_003.0,
    val closedSpotxStockUsd: Double = 99_935.0,
    val duplicateStockCodesUsd: Double = 156_381.0,
    val realizationCoeff: Double = 0.5,
    val maxAnnualGainUsd: Double = 148_000.0,
    val recommendedAnnualCostUsd: Double = 7_080.0
) {
    val realAnnualGainUsd: Double
        get() = maxAnnualGainUsd * realizationCoeff

    val netAnnualProfitImprovementUsd: Double
        get() = realAnnualGainUsd - recommendedAnnualCostUsd

    val roiMultiplier: Double
        get() = if (recommendedAnnualCostUsd > 0) netAnnualProfitImprovementUsd / recommendedAnnualCostUsd else 0.0

    val paybackMonths: Double
        get() = if (realAnnualGainUsd > 0) recommendedAnnualCostUsd / (realAnnualGainUsd / 12.0) else 0.0
}
