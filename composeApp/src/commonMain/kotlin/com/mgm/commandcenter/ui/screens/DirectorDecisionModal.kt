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
import com.mgm.commandcenter.model.DirectorOutcome
import com.mgm.commandcenter.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectorDecisionModal(
    initialOutcome: DirectorOutcome = DirectorOutcome.CONDITIONAL,
    onDismiss: () -> Unit,
    onConfirmDecision: (DirectorOutcome, String) -> Unit
) {
    var selectedOutcome by remember { mutableStateOf(initialOutcome) }
    var conditionText by remember {
        mutableStateOf(
            "Samarqand safari xarajatlari 1.5 mln so'mdan oshmasligi kerak. Safar tugagach 3 ish kuni ichida B2B shartnoma nusxasi va buxgalteriya cheklari taqdim etilsin."
        )
    }

    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⚖️", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Qaror qabul qilish (EMJ-SOP-ADM-01)",
                            style = MaterialTheme.typography.titleMedium,
                            color = WarmCharcoal,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Alisher Po'latov — B2B Xizmat safari (27-28 may)",
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryColor
                    )
                }
                IconButton(onClick = onDismiss) {
                    Text("✕", fontSize = 16.sp, color = SecondaryColor)
                }
            }

            // Outcome Selector Grid (2x2)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "QAROR TURI (SOP REGLAMENTI)",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TerraOutline
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Approve
                    DecisionOutcomeCard(
                        outcome = DirectorOutcome.APPROVE,
                        isSelected = selectedOutcome == DirectorOutcome.APPROVE,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedOutcome = DirectorOutcome.APPROVE }
                    )
                    // Conditional
                    DecisionOutcomeCard(
                        outcome = DirectorOutcome.CONDITIONAL,
                        isSelected = selectedOutcome == DirectorOutcome.CONDITIONAL,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedOutcome = DirectorOutcome.CONDITIONAL }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Need Info
                    DecisionOutcomeCard(
                        outcome = DirectorOutcome.NEED_INFO,
                        isSelected = selectedOutcome == DirectorOutcome.NEED_INFO,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedOutcome = DirectorOutcome.NEED_INFO }
                    )
                    // Reject
                    DecisionOutcomeCard(
                        outcome = DirectorOutcome.REJECT,
                        isSelected = selectedOutcome == DirectorOutcome.REJECT,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedOutcome = DirectorOutcome.REJECT }
                    )
                }
            }

            // Condition & Instructions Area
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text("Qaror sharti yoki ko'rsatma", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                        Text(" *", color = TerraError, fontWeight = FontWeight.Bold)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SecondaryContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("SOP TALABI", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = OnSecondaryContainer)
                    }
                }

                OutlinedTextField(
                    value = conditionText,
                    onValueChange = { conditionText = it },
                    modifier = Modifier.fillMaxWidth().height(110.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLow,
                        unfocusedContainerColor = SurfaceContainerLow,
                        focusedBorderColor = ForestGreen
                    )
                )

                // Ready templates chips
                Text("Tayyor shablon ko'rsatmalar:", fontSize = 11.sp, color = SecondaryColor, fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(
                        "Xarajat limitiga rioya qilish" to " Safar xarajati 1.5 mln so'mdan oshmasin.",
                        "Hisobot 3 kunda" to " Safar tugagach 3 kunda hisobot berilsin.",
                        "O'rinbosar bilan" to " Barcha ishlarni Jamshid Saidov bilan kelishing."
                    ).forEach { (label, snippet) ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainer)
                                .border(1.dp, TerraOutlineVariant.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                .clickable { conditionText += snippet }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("+ $label", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = ForestGreen)
                        }
                    }
                }
            }

            // Document Impact Notice
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLow)
                    .border(1.dp, TerraOutlineVariant.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text("📋", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Hujjatga biriktirish: Ushbu izoh va shart avtomatik ravishda EMJ-SOP-ADM-01 .docx hujjatining 'Rahbar xulosasi' bandiga kiritiladi va xodimga push-bildirishnoma yuboriladi.",
                        fontSize = 11.sp,
                        color = Color(0xFF4A4E4A),
                        lineHeight = 16.sp
                    )
                }
            }

            // Digital Signature stamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✓", color = ForestGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("24-may 14:45 • Operatsion Direktor", fontSize = 11.sp, color = TerraOutline)
                }
                Text("ID: 884-OD", fontSize = 10.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, color = SecondaryColor)
            }

            // Action Buttons
            Button(
                onClick = {
                    onConfirmDecision(selectedOutcome, conditionText)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth().height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
            ) {
                Text("✓ Qarorni tasdiqlash va yuborish", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
            }

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Bekor qilish", color = SecondaryColor, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun DecisionOutcomeCard(
    outcome: DirectorOutcome,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SecondaryContainer else SurfaceContainerLow
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(
                if (isSelected) WarmAmber else TerraOutlineVariant.copy(alpha = 0.3f)
            )
        )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    when (outcome) {
                        DirectorOutcome.APPROVE -> "✓"
                        DirectorOutcome.CONDITIONAL -> "📋"
                        DirectorOutcome.NEED_INFO -> "❓"
                        DirectorOutcome.REJECT -> "✗"
                    },
                    fontSize = 14.sp
                )
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) ForestGreen else Color.Transparent)
                        .border(1.5.dp, if (isSelected) ForestGreen else TerraOutlineVariant, CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(outcome.titleUz, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = WarmCharcoal)
            Text(outcome.subtitleUz, fontSize = 10.sp, color = SecondaryColor)
        }
    }
}
