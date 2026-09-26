package com.mgm.commandcenter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mgm.commandcenter.data.CommandCenterRepository
import com.mgm.commandcenter.data.MockDataProvider
import com.mgm.commandcenter.model.ExecutiveFiveNumbers
import com.mgm.commandcenter.model.FinancialHealthSummary
import com.mgm.commandcenter.theme.*

@Composable
fun DashboardScreen(
    numbers: ExecutiveFiveNumbers,
    financial: FinancialHealthSummary,
    onBackClick: () -> Unit
) {
    var coeff by remember { mutableStateOf(financial.realizationCoeff) }
    val scrollState = rememberScrollState()

    val realGain = financial.maxAnnualGainUsd * coeff
    val netGain = realGain - financial.recommendedAnnualCostUsd
    val roi = if (financial.recommendedAnnualCostUsd > 0) netGain / financial.recommendedAnnualCostUsd else 0.0
    val payback = if (realGain > 0) financial.recommendedAnnualCostUsd / (realGain / 12.0) else 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Text("←", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                }
                Column {
                    Text("Раҳбар Дашборди (5 Рақам)", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    Text("ЭМЖИЕМ Молиявий ва Операцион Ҳолат", fontSize = 11.sp, color = SecondaryColor)
                }
            }
        }

        // 1. Five Numbers Bento Grid
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("5 ТА АСОСИЙ КУНДАЛИК РАҚАМ", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerraOutline)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard(
                    title = "1. Касса қолдиғи",
                    value = "184.2 млн сўм",
                    subtext = "Банк + Нақд",
                    modifier = Modifier.weight(1f),
                    color = ForestGreen
                )
                MetricCard(
                    title = "2. Кечаги сотув",
                    value = "42.5 млн сўм",
                    subtext = "Kunlik ko‘rsatkich",
                    modifier = Modifier.weight(1f),
                    color = ForestGreen
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard(
                    title = "3. Захира қиймати",
                    value = "$486,733",
                    subtext = "256 кунлик мол (1.43х)",
                    modifier = Modifier.weight(1f),
                    color = WarmAmber
                )
                MetricCard(
                    title = "4. Мижоз қарзи",
                    value = "215.8 млн сўм",
                    subtext = "Дебиторлик",
                    modifier = Modifier.weight(1f),
                    color = WarmCharcoal
                )
            }

            MetricCard(
                title = "5. Бугунги режалаштирилган тўловлар",
                value = "38.1 млн сўм",
                subtext = "Тўлов дарвозаси (B1) назоратида",
                modifier = Modifier.fillMaxWidth(),
                color = ForestGreen
            )
        }

        // 2. Strict Stop-Rule Card (A2 Agent check)
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(ForestGreen)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("A2 Қатъий хавф қоидаси ҳолати:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "«Захира ошиб, касса тушса -> барча янги буюртма тўхтайди.»",
                    fontSize = 11.sp,
                    color = SecondaryColor
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryFixed)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("✓ Ҳолат нормал: Хавф аниқланмади", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                }
            }
        }

        // 3. Interactive Realization Coefficient & ROI Calculator
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Реаллашиш коэффициенти", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    Text("${(coeff * 100).toInt()}% ($coeff)", fontWeight = FontWeight.Bold, color = ForestGreen)
                }
                Text("Эҳтиёткор фараз (0.5 тавсия этилади, 1.0 = оптимист)", fontSize = 11.sp, color = SecondaryColor)

                Slider(
                    value = coeff.toFloat(),
                    onValueChange = {
                        coeff = it.toDouble()
                        CommandCenterRepository.instance.setRealizationCoefficient(coeff)
                    },
                    valueRange = 0.1f..1.0f,
                    steps = 8,
                    colors = SliderDefaults.colors(thumbColor = ForestGreen, activeTrackColor = ForestGreen)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Йиллик реал фойда:", fontSize = 11.sp, color = SecondaryColor)
                        Text("$${realGain.toInt()}", style = MaterialTheme.typography.titleMedium, color = ForestGreen)
                    }
                    Column {
                        Text("Соф ютуқ:", fontSize = 11.sp, color = SecondaryColor)
                        Text("$${netGain.toInt()}", style = MaterialTheme.typography.titleMedium, color = ForestGreen)
                    }
                    Column {
                        Text("ROI қайтими:", fontSize = 11.sp, color = SecondaryColor)
                        Text("${(roi * 10).toInt() / 10.0}x", style = MaterialTheme.typography.titleMedium, color = WarmAmber)
                    }
                    Column {
                        Text("Қоплаш муддати:", fontSize = 11.sp, color = SecondaryColor)
                        Text("${(payback * 10).toInt() / 10.0} ой", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    }
                }
            }
        }

        // 4. 90-Day Implementation Plan (6 stages)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("90 КУНЛИК БОСҚИЧМА-БОСҚИЧ РЕЖА", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerraOutline)

            MockDataProvider.planStages.forEach { stage ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.2f)))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${stage.stageNumber}-БОСҚИЧ: ${stage.stageName}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                            Text(stage.daysRange, fontSize = 10.sp, color = SecondaryColor)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            stage.agentCodes.forEach { c ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(PrimaryFixed)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(c, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text("Иш ҳажми: ${stage.workloadHours} соат", fontSize = 10.sp, color = TerraOutline)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(stage.goalDescription, fontSize = 11.sp, color = Color(0xFF4A4E4A))
                    }
                }
            }
        }

        // 5. Unnecessary Agents (Пул сарфламанг)
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SecondaryContainer.copy(alpha = 0.5f)),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("🚫 КЕРАКСИЗ АГЕНТЛАР — ПУЛ САРФЛАМАНГ", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmAmber)
                Text("Тежаладиган пул: йилига ~$9,000–$12,000 ва 60 соат иш.", fontSize = 11.sp, color = WarmCharcoal)

                MockDataProvider.unnecessaryAgents.forEach { ua ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLowest)
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${ua.code}: ${ua.name}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = TerraError)
                            Text(ua.conclusion, fontWeight = FontWeight.Bold, fontSize = 10.sp, color = TerraError)
                        }
                        Text("Нега керак эмас: ${ua.whyNotNeeded}", fontSize = 10.sp, color = SecondaryColor, lineHeight = 14.sp)
                        Text("Ўрнига: ${ua.alternativeSolution}", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = ForestGreen)
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtext: String,
    modifier: Modifier = Modifier,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.25f)))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 10.sp, color = SecondaryColor, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = color)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtext, fontSize = 9.sp, color = TerraOutline)
        }
    }
}
