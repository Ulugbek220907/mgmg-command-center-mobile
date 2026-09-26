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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mgm.commandcenter.model.DirectorOutcome
import com.mgm.commandcenter.model.SopPermissionRequest
import com.mgm.commandcenter.theme.*

@Composable
fun SopDetailScreen(
    sop: SopPermissionRequest,
    onBackClick: () -> Unit,
    onOpenDecisionModal: (DirectorOutcome) -> Unit
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
        // Top Bar
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
                    Text("Ariza: ${sop.id}", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    Text("№ ${sop.trackingNumber} • 24-may, 2024", fontSize = 11.sp, color = SecondaryColor)
                }
            }
            Row {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainer)
                        .clickable {},
                    contentAlignment = Alignment.Center
                ) {
                    Text("📥", fontSize = 14.sp)
                }
            }
        }

        // 1. Status Banner
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(WarmAmber)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Holat:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SecondaryColor)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(sop.status.badgeColorHex))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "⏳ ${sop.status.titleUz}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnTertiaryFixed
                    )
                }
            }
        }

        // 2. Requester Info Bento Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(PrimaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(sop.requesterInitials, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnPrimaryFixedVariant)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(sop.requesterName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = WarmCharcoal)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("✓", color = ForestGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(sop.requesterRole, fontSize = 11.sp, color = SecondaryColor)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("ID: #4092", fontSize = 10.sp, color = SecondaryColor)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Sana va vaqt:", fontSize = 11.sp, color = SecondaryColor)
                    Text("24-may, 2024 • 14:30", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = WarmCharcoal)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("O‘rinbosar:", fontSize = 11.sp, color = SecondaryColor)
                    Text(sop.substitutePerson, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = WarmCharcoal)
                }
            }
        }

        // 3. Official SOP Document Details
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Ruxsatnoma tafsilotlari", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PrimaryFixed.copy(alpha = 0.4f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("EMJ-SOP-ADM-01", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                    }
                }

                // Section 1
                Column {
                    Text("1. RUXSATNOMA TURI", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerraOutline)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLow)
                            .padding(10.dp)
                    ) {
                        Text(sop.category.titleUz + " (Viloyatlararo B2B uchrashuv)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = WarmCharcoal)
                    }
                }

                // Section 2
                Column {
                    Text("2. MUDDAT VA VAQT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerraOutline)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLow)
                            .padding(10.dp)
                    ) {
                        Text(sop.datesRange, fontSize = 12.sp, color = WarmCharcoal)
                    }
                }

                // Section 3: Official Cyrillic Legal Text
                Column {
                    Text("3. ASOS VA TUSHUNTIRISH (РАСМИЙ КИРИЛЛ ШАКЛИ)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerraOutline)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLow)
                            .border(1.dp, ForestGreen.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🏛️ Расмий ҳужжат баёни", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SecondaryColor)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("• МГМГ Регламенти", fontSize = 10.sp, color = TerraOutline)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = sop.cyrillicOfficialText,
                                fontStyle = FontStyle.Italic,
                                fontSize = 12.sp,
                                color = WarmCharcoal,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                // Section 4: Limit
                Column {
                    Text("4. MOLIYAVIY VA TRANSPORT XARAJATLARI", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerraOutline)
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
                            Text("Limit:", fontSize = 12.sp, color = SecondaryColor)
                            Text(sop.expenseLimitText, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                        }
                    }
                }
            }
        }

        // 4. Document Action Card (.docx)
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📄", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(sop.documentFilename, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                        Text("Tayyor rasmiy hujjat (imzo uchun tayyorlangan)", fontSize = 10.sp, color = SecondaryColor)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Ko‘rish", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                    ) {
                        Text("Yuklab olish (.docx)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                    }
                }
            }
        }

        // 5. Approver Action Section (SOP Rule - 4 outcomes)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚖️", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Direktor qarori (4 ta standart natija):", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                }

                // Outcome 1: Approve
                Button(
                    onClick = { onOpenDecisionModal(DirectorOutcome.APPROVE) },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Text("✓ Tasdiqlash (Qabul qilindi)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = OnPrimary)
                }

                // Outcome 2: Conditional
                Button(
                    onClick = { onOpenDecisionModal(DirectorOutcome.CONDITIONAL) },
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryContainer)
                ) {
                    Text("📋 Shartli tasdiqlash", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = OnSecondaryContainer)
                }

                // Outcome 3: Need Info
                OutlinedButton(
                    onClick = { onOpenDecisionModal(DirectorOutcome.NEED_INFO) },
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("❓ Qo‘shimcha ma’lumot so‘rash", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = WarmCharcoal)
                }

                // Outcome 4: Reject
                Button(
                    onClick = { onOpenDecisionModal(DirectorOutcome.REJECT) },
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerraErrorContainer)
                ) {
                    Text("✗ Rad etish", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = TerraError)
                }

                Text(
                    text = "Izoh: Har qanday qaror tizim audit jurnaliga va rasmiy reyestrga yoziladi.",
                    fontSize = 11.sp,
                    fontStyle = FontStyle.Italic,
                    color = TerraOutline,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
