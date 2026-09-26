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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mgm.commandcenter.model.UserProfile
import com.mgm.commandcenter.theme.*

@Composable
fun SettingsScreen(
    profile: UserProfile,
    onLogoutClick: () -> Unit
) {
    var pushDailyReport by remember { mutableStateOf(profile.isDailyReportPushEnabled) }
    var pushReminder by remember { mutableStateOf(profile.isReminderPushEnabled) }
    var pushSop by remember { mutableStateOf(profile.isSopPushEnabled) }

    val scrollState = rememberScrollState()

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
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⚙️", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("PRIMUS COMMAND", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SecondaryColor, letterSpacing = 1.sp)
                    Text("Sozlamalar va Profil", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                }
            }
        }

        // 1. Profile Identity Card (Xodim pasporti)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f))),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(SecondaryContainer)
                            .border(1.dp, TerraOutlineVariant.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(profile.initials, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = WarmAmber)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(profile.fullName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = WarmCharcoal)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(PrimaryFixed)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("Faol xodim", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                            }
                        }
                        Text("${profile.roleTitle} • ${profile.companyDivision}", fontSize = 12.sp, color = SecondaryColor)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("Tizim kodi (ID)", fontSize = 10.sp, color = SecondaryColor)
                            Text(profile.employeeCode, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = WarmCharcoal)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("Biriktirilgan hudud", fontSize = 10.sp, color = SecondaryColor)
                            Text(profile.assignedRegion, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = WarmCharcoal)
                        }
                    }
                }
            }
        }

        // 2. Bildirishnomalar va Eslatmalar (Push Notifications)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Bildirishnomalar va Eslatmalar", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("SOP qoidalari", fontSize = 10.sp, color = SecondaryColor)
                    }
                }

                // Toggle 1
                NotificationToggleRow(
                    title = "Kunlik hisobot (16:00)",
                    badge = "Majburiy",
                    description = "Har ish kuni hisobot topshirish boshlanganda",
                    isChecked = pushDailyReport,
                    onCheckedChange = { pushDailyReport = it }
                )

                HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.2f))

                // Toggle 2
                NotificationToggleRow(
                    title = "Hisobot eslatmasi (17:00)",
                    badge = null,
                    description = "Agar hisobot topshirilmagan bo'lsa",
                    isChecked = pushReminder,
                    onCheckedChange = { pushReminder = it }
                )

                HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.2f))

                // Toggle 3
                NotificationToggleRow(
                    title = "Ruxsatnoma holatlari (SOP)",
                    badge = null,
                    description = "Direktor qarori va o'zgarishlar to'g'risida",
                    isChecked = pushSop,
                    onCheckedChange = { pushSop = it }
                )

                // Push device status
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(profile.connectedDevice, fontSize = 11.sp, color = Color(0xFF4A4E4A))
                        Text("● Ulangan", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                    }
                }
            }
        }

        // 3. Dastur va Tizim ma'lumotlari
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Dastur va Tizim ma'lumotlari", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)

                SystemInfoRow(label = "Til (Language)", value = "O'zbekcha (Lotin)")
                HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.2f))
                SystemInfoRow(label = "SOP formati", value = "O'zbekcha (Kirill) - Rasmiy")
                HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.2f))
                SystemInfoRow(label = "Dastur versiyasi", value = profile.appVersion)
                HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.2f))
                SystemInfoRow(label = "Vaqt mintaqasi", value = profile.timezone)
            }
        }

        // 4. Xavfsizlik va Seans
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Xavfsizlik va Seans", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerLow)
                        .padding(10.dp)
                ) {
                    Column {
                        Text("Faol seans: Ushbu qurilma", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                        Text("SecureStore shifrlangan ma'lumotlar ombori", fontSize = 11.sp, color = SecondaryColor)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Telegram orqali bog'lanish", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = WarmCharcoal)
                        Text(profile.telegramUsername, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                    }
                    Text("↗", fontSize = 16.sp, color = ForestGreen)
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Logout Button
                OutlinedButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TerraError),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TerraError.copy(alpha = 0.4f))
                ) {
                    Text("🚪 Tizimdan chiqish (Log out)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TerraError)
                }

                Text(
                    text = "Chiqish amalga oshirilganda ushbu qurilmadagi tokenlar o'chiriladi va qayta kirish uchun yangi taklif kodi talab qilinadi.",
                    fontSize = 10.sp,
                    color = TerraOutline,
                    lineHeight = 14.sp
                )
            }
        }

        // App Branding / Footer
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("PRIMUS MANAGEMENT GROUP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryColor, letterSpacing = 1.sp)
            Text("Barcha huquqlar himoyalangan • Ichki foydalanish uchun", fontSize = 10.sp, color = TerraOutline)
        }
    }
}

@Composable
private fun NotificationToggleRow(
    title: String,
    badge: String?,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                if (badge != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(TertiaryFixed)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(badge, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = OnTertiaryFixed)
                    }
                }
            }
            Text(description, fontSize = 11.sp, color = SecondaryColor)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = OnPrimary,
                checkedTrackColor = ForestGreen
            )
        )
    }
}

@Composable
private fun SystemInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 12.sp, color = SecondaryColor)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = WarmCharcoal)
    }
}
