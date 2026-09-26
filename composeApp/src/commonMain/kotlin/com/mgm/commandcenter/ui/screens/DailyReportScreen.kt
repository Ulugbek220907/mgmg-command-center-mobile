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
import com.mgm.commandcenter.model.DailyReport
import com.mgm.commandcenter.model.DailyReportHistoryItem
import com.mgm.commandcenter.model.ReportStatus
import com.mgm.commandcenter.theme.*

@Composable
fun DailyReportScreen(
    report: DailyReport,
    historyList: List<DailyReportHistoryItem>,
    onSubmitReport: (summary: String, followUpAnswer: String) -> Boolean,
    onBackClick: () -> Unit
) {
    var textContent by remember { mutableStateOf(report.textContent) }
    var followUpAnswer by remember { mutableStateOf(report.aiFollowUpAnswer) }
    var submitSuccessMessage by remember { mutableStateOf<String?>(null) }
    var validationError by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Return Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Text("←", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
            }
            Column {
                Text("Kunlik hisobot", style = MaterialTheme.typography.headlineSmall, color = ForestGreen)
                Text(report.dateString, style = MaterialTheme.typography.bodySmall, color = SecondaryColor)
            }
        }

        // 1. Submission Window Banner
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.4f)))
        ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SecondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⏰", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Topshirish qoidalari", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = WarmCharcoal)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(PrimaryFixed)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text("Faol vaqt", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OnPrimaryFixedVariant)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Hisobot qabul qilish: 16:00 – 00:00 gacha. Yarim tundan keyin qabul qilinmaydi.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF4A4E4A)
                    )
                }
            }
        }

        // 2. Active Form Section
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f))),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text("Bugun nimalar bajardingiz?", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                        Text(" *", color = TerraError, fontWeight = FontWeight.Bold)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ForestGreen)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Tahrirlanmoqda", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = ForestGreen)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = textContent,
                    onValueChange = {
                        if (it.length <= 1000) {
                            textContent = it
                            validationError = null
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text(
                            "Bajarilgan asosiy ishlar, uchrashuvlar, natijalar va to‘siqlarni qisqacha bayon qiling...",
                            fontSize = 12.sp,
                            color = TerraOutline
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLow,
                        focusedBorderColor = ForestGreen,
                        unfocusedBorderColor = TerraOutlineVariant.copy(alpha = 0.4f)
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tavsiya: Kamida 50 ta belgi",
                        fontSize = 11.sp,
                        color = if (textContent.length < 50) TerraError else SecondaryColor
                    )
                    Text(
                        text = "${textContent.length} / 1000 belgi",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreen
                    )
                }

                if (validationError != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(validationError!!, color = TerraError, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                if (submitSuccessMessage != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(submitSuccessMessage!!, color = ForestGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (textContent.trim().length < 50) {
                            validationError = "Xato: Hisobot kamida 50 ta belgidan iborat bo‘lishi shart!"
                        } else {
                            val ok = onSubmitReport(textContent, followUpAnswer)
                            if (ok) {
                                submitSuccessMessage = "Hisobot muvaffaqiyatli yuborildi va qabul qilindi!"
                                validationError = null
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Text("Hisobotni yuborish", fontWeight = FontWeight.Bold, color = OnPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("➤", fontSize = 14.sp)
                }
            }
        }

        // 3. AI Follow-up Question Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TertiaryFixed.copy(alpha = 0.8f)))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(WarmAmber),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🤖", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("AI aniqlashtiruvchi savoli", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmAmber)
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SecondaryContainer)
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text("Faqat 1 marta beriladi", fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = OnSecondaryContainer)
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = report.aiFollowUpQuestion,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = WarmCharcoal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = followUpAnswer,
                    onValueChange = { followUpAnswer = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text("Tezkor javob yozing...", fontSize = 12.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = ForestGreen
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onSubmitReport(textContent, followUpAnswer)
                        submitSuccessMessage = "Javob tasdiqlandi va hisobotga kiritildi!"
                    },
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor)
                ) {
                    Text("✓ Javobni tasdiqlash", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        // 4. Last 14 Days History Section
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Oxirgi 14 kunlik hisobotlar", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                Text("Barchasi >", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
            }

            historyList.forEach { item ->
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
                            Text(item.dateLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WarmCharcoal)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(item.status.badgeColorHex))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = item.status.titleUz,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(item.status.badgeTextColorHex)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.contentSnippet,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF4A4E4A),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
