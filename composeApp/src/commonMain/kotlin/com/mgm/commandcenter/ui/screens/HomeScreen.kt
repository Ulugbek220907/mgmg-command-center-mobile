package com.mgm.commandcenter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mgm.commandcenter.model.DailyReport
import com.mgm.commandcenter.model.SopPermissionRequest
import com.mgm.commandcenter.theme.*

@Composable
fun HomeScreen(
    dailyReport: DailyReport,
    latestSop: SopPermissionRequest,
    onNavigateToWriteReport: () -> Unit,
    onNavigateToNewSop: () -> Unit,
    onNavigateToSopDetail: () -> Unit,
    onOpenAgentsTracker: () -> Unit,
    onOpenExecutiveDashboard: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Greeting & Date Widget
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "XUSH KELIBSIZ",
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondaryColor,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Assalomu alaykum, Alisher",
                            style = MaterialTheme.typography.headlineSmall,
                            color = WarmCharcoal,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerHighest)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Primus Laundry",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WarmCharcoal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📅", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "24-may, 2024, Juma",
                            fontSize = 12.sp,
                            color = WarmCharcoal,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⏱", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "16:42 • Asia/Tashkent",
                            fontSize = 11.sp,
                            color = SecondaryColor
                        )
                    }
                }
            }
        }

        // 2. Hero Action Card: Bugungi hisobot holati
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.4f))),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(TertiaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📝", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Bugungi hisobot holati",
                                style = MaterialTheme.typography.titleMedium,
                                color = WarmCharcoal
                            )
                            Text(
                                text = "Kundalik B2B sotuv hisoboti",
                                style = MaterialTheme.typography.bodySmall,
                                color = SecondaryColor
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(TertiaryFixed)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(WarmAmber)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (dailyReport.isSubmitted) "Topshirildi" else "Kutilmoqda",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnTertiaryFixed
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLow)
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Qabul qilish oralig‘i:", fontSize = 12.sp, color = Color(0xFF4A4E4A))
                            Text("16:00 dan 00:00 gacha", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WarmCharcoal)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(SurfaceContainerHighest)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.35f)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(WarmAmber)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("⏰ Eslatma: 17:00 da", fontSize = 11.sp, color = WarmAmber, fontWeight = FontWeight.SemiBold)
                            Text(
                                text = if (dailyReport.isSubmitted) "Bajarildi ✓" else "Topshirilmagan",
                                fontSize = 11.sp,
                                color = if (dailyReport.isSubmitted) ForestGreen else TerraError,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onNavigateToWriteReport,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Text("✏️", fontSize = 15.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Hisobot yozish", fontWeight = FontWeight.Bold, color = OnPrimary)
                }
            }
        }

        // 3. AI Quick Access Banners (21 Agents Hub + 5 Numbers Dashboard)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOpenAgentsTracker() },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.4f)))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("🤖", fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("21 AI Agent", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    Text("Treker & Holat", style = MaterialTheme.typography.bodySmall, color = SecondaryColor)
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOpenExecutiveDashboard() },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.4f)))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("📈", fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("5 Рақам", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    Text("Касса ва хавф", style = MaterialTheme.typography.bodySmall, color = SecondaryColor)
                }
            }
        }

        // 4. Inline Alert / AI Nazorat Tizimi
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SecondaryContainer.copy(alpha = 0.7f)),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowest),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🧠", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("AI Nazorat Tizimi", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(ForestGreen.copy(alpha = 0.15f))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text("Avtomat", fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = ForestGreen)
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Diqqat: Hisobot qisqa yoki noaniq bo‘lsa, AI tizimi 1 ta aniqlashtiruvchi savol beradi.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF4A4E4A)
                    )
                }
            }
        }

        // 5. Quick Status Section: Ruxsatnomalar (SOP)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🛡️", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ruxsatnomalar (SOP)", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(PrimaryFixed)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("1 ta ko‘rib chiqilmoqda", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OnPrimaryFixedVariant)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Latest app card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.2f))),
                    modifier = Modifier.clickable { onNavigateToSopDetail() }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Text(
                                    text = "Oxirgi ariza: ${latestSop.title}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = WarmCharcoal
                                )
                                Text(
                                    text = latestSop.id,
                                    fontSize = 11.sp,
                                    color = SecondaryColor,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(TertiaryFixed.copy(alpha = 0.6f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("⏳ ${latestSop.status.titleUz}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = WarmAmber)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "HR va Direktor ko'rib chiqish muddati: 24 soat ichida",
                            fontSize = 11.sp,
                            color = Color(0xFF4A4E4A)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onNavigateToNewSop,
                        modifier = Modifier.weight(1f).height(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ForestGreen)
                    ) {
                        Text("➕ Yangi ariza", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = onNavigateToSopDetail,
                        modifier = Modifier.weight(1f).height(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                    ) {
                        Text("Ko‘rish", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                    }
                }
            }
        }

        // 6. Director Brief Preview Widget
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ForestGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Direktor brifi & Rejalar", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    }
                    Text("Bugungi ko‘rsatma", fontSize = 11.sp, color = SecondaryColor)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .border(width = 1.dp, color = ForestGreen.copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "\"B2B tarmoqlari: Mehmonxonalar va yirik restoranlar segmentida yozgi mavsumiy buyurtmalarni qayta muvofiqlashtirishga e'tibor qarating.\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF4A4E4A),
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Primus Boshqarmasi • 09:00", fontSize = 11.sp, color = TerraOutline)
                    Text("Tanishildi ✓", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                }
            }
        }
    }
}
