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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mgm.commandcenter.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit
) {
    var inviteCode by remember { mutableStateOf("PRM-8492-TX09") }
    var biometricEnabled by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .verticalScroll(scrollState)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Top Bar Badging
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceContainer)
                        .border(1.dp, TerraOutlineVariant.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ForestGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("MGMG Node • Primus", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = WarmCharcoal)
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("🔒 SSL v3 Encrypted", fontSize = 10.sp, color = SecondaryColor, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Icon Crest & Title
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, TerraOutlineVariant.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🛡️", fontSize = 26.sp)
            }

            Column {
                Text(
                    text = "MGMG Command tizimiga kirish",
                    style = MaterialTheme.typography.headlineMedium,
                    color = WarmCharcoal,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Bir martalik taklif kodi orqali autentifikatsiya",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryColor
                )
            }

            // Main Authentication Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f))),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Taklif kodi (Invite Code) *", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(SecondaryContainer)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("PRM-XXXX-XXXX", fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = WarmAmber, fontWeight = FontWeight.Bold)
                        }
                    }

                    OutlinedTextField(
                        value = inviteCode,
                        onValueChange = {
                            inviteCode = it.uppercase()
                            errorMessage = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            if (inviteCode.length >= 8) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryFixed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("✓", color = ForestGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = WarmCreamBg,
                            unfocusedContainerColor = WarmCreamBg,
                            focusedBorderColor = ForestGreen
                        )
                    )

                    Text(
                        text = "Ushbu kodni Telegram MGMG Admin boti (@mgmg_admin_bot) orqali olishingiz mumkin.",
                        fontSize = 11.sp,
                        color = SecondaryColor
                    )

                    // Telegram Button
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("✈️ Telegram orqali kod olish", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                    }

                    // 15-min validity notice
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Text("⏱️", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("15 daqiqalik amal qilish muddati", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = WarmCharcoal)
                                Text(
                                    "Kodingiz 15 daqiqa davomida amal qiladi va qurilma xotirasida xavfsiz saqlanadi.",
                                    fontSize = 10.sp,
                                    color = Color(0xFF4A4E4A)
                                )
                            }
                        }
                    }

                    if (errorMessage != null) {
                        Text(errorMessage!!, color = TerraError, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    // CTA Button
                    Button(
                        onClick = {
                            if (inviteCode.length < 8) {
                                errorMessage = "Kodni to‘liq kiriting (masalan: PRM-8492-TX09)"
                            } else {
                                onLoginSuccess(inviteCode)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                    ) {
                        Text("Tizimga kirish", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OnPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("➔", fontSize = 16.sp)
                    }
                }
            }

            // Biometric Capsule
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("👆", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Biometrik kalit bilan bog'lash", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WarmCharcoal)
                            Text("Kirgandan so'ng FaceID / TouchID faollashadi", fontSize = 10.sp, color = SecondaryColor)
                        }
                    }
                    Checkbox(
                        checked = biometricEnabled,
                        onCheckedChange = { biometricEnabled = it },
                        colors = CheckboxDefaults.colors(checkedColor = ForestGreen)
                    )
                }
            }
        }

        // Footer
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("MGMG Xavfsizlik protokoli: Parol talab etilmaydi.", fontSize = 10.sp, color = SecondaryColor)
            Text("Asia/Tashkent • v1.0.4 Primus IT", fontSize = 10.sp, color = TerraOutline)
        }
    }
}
