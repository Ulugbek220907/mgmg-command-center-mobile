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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mgm.commandcenter.data.CommandCenterRepository
import com.mgm.commandcenter.model.SopCategory
import com.mgm.commandcenter.theme.*

@Composable
fun SopRequestScreen(
    onBackClick: () -> Unit,
    onSubmitComplete: () -> Unit
) {
    var currentStep by remember { mutableStateOf(2) } // Step 2 is Sabab & Asoslash
    var selectedCategory by remember { mutableStateOf(SopCategory.BUSINESS_TRIP) }
    var reasonText by remember {
        mutableStateOf("B2B mijozlarimiz (Samarqand mehmonxonalari tarmog‘i) bilan yangi shartnoma imzolash va yillik servis kelishuvini yakunlash uchun 2 kunlik xizmat safari zarur.")
    }
    var substitutePerson by remember { mutableStateOf("Jamshid Saidov (B2B Sotuv o'rinbosari)") }
    var datesRange by remember { mutableStateOf("27-may, 2024 (09:00) — 28-may, 2024 (18:00)") }
    var expenseLimit by remember { mutableStateOf("1,800,000 UZS") }

    val cyrillicPreview = remember(reasonText) {
        CommandCenterRepository.instance.convertToCyrillicLegal(reasonText)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
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
                    Text("Yozma ruxsatnoma", style = MaterialTheme.typography.headlineSmall, color = WarmCharcoal)
                    Text("EMJ-SOP-ADM-01 standarti bo‘yicha", fontSize = 11.sp, color = SecondaryColor)
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(SecondaryContainer)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("✓ SOP-ADM-01", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OnSecondaryContainer)
            }
        }

        // 1. Wizard Stepper Indicator Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("BOSQICH $currentStep / 4", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                    Text(
                        when (currentStep) {
                            1 -> "Asosiy ma'lumotlar"
                            2 -> "Sabab va asoslash"
                            3 -> "Muddat va limit"
                            else -> "Ko‘rish va tasdiqlash"
                        },
                        fontSize = 11.sp,
                        color = SecondaryColor
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(SurfaceContainerHighest)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(currentStep / 4f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(3.dp))
                            .background(ForestGreen)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("1. Asosiy", "2. Sabab", "3. Muddat", "4. Ko‘rish").forEachIndexed { index, stepName ->
                        val stepNum = index + 1
                        val isDone = stepNum < currentStep
                        val isCurrent = stepNum == currentStep
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { currentStep = stepNum }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isDone -> ForestGreen
                                            isCurrent -> PrimaryContainer
                                            else -> SurfaceContainerHighest
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isDone) "✓" else "$stepNum",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDone) OnPrimary else if (isCurrent) OnPrimaryFixedVariant else SecondaryColor
                                )
                            }
                            Text(
                                text = stepName,
                                fontSize = 10.sp,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                color = if (isCurrent) ForestGreen else SecondaryColor
                            )
                        }
                    }
                }
            }
        }

        // 2. Ruxsat turi Selection Pills
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Ruxsatnoma toifasi (SOP bandi)", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SopCategory.entries.forEach { cat ->
                    val isSelected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) ForestGreen else SurfaceContainer)
                            .border(1.dp, if (isSelected) ForestGreen else TerraOutlineVariant.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat.titleUz,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) OnPrimary else WarmCharcoal
                        )
                    }
                }
            }
        }

        // 3. Active Question Card: Detailed Reason Input
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Ruxsatnoma sababi va mufassal asosi",
                    style = MaterialTheme.typography.titleMedium,
                    color = WarmCharcoal
                )
                Text(
                    text = "Nima sababdan ruxsatnoma so‘rayapsiz? Maqsad, kutilayotgan natija va asosiy sabablarni bayon qiling.",
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryColor
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = reasonText,
                    onValueChange = { reasonText = it },
                    modifier = Modifier.fillMaxWidth().height(110.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = ForestGreen
                    )
                )

                val isReasonLengthValid = reasonText.trim().length >= 20
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isReasonLengthValid) {
                        Text("✓ Minimal hajm talabiga javob beradi (≥20 belgi)", fontSize = 11.sp, color = ForestGreen, fontWeight = FontWeight.SemiBold)
                    } else {
                        Text("⚠️ Kamida 20 ta belgi kiritilishi shart (yana ${20 - reasonText.trim().length} ta)", fontSize = 11.sp, color = TerraError, fontWeight = FontWeight.SemiBold)
                    }
                    Text("${reasonText.length} / 500 belgi", fontSize = 11.sp, color = if (isReasonLengthValid) SecondaryColor else TerraError)
                }
            }
        }

        // 4. AI Checking / Feedback Inline Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SecondaryContainer.copy(alpha = 0.7f)),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TertiaryFixed.copy(alpha = 0.5f)))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🧠", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("AI tekshiruvi: Muvaffaqiyatli ✓", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = ForestGreen)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerLowest)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("SOP Maslahatchi", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ariza matni aniq va tushunarli. EMJ-SOP qoidalariga mos. Ushbu matn avtomatik ravishda rasmiy o‘zbek (kirill) yozuviga o‘girildi:",
                    fontSize = 11.sp,
                    color = Color(0xFF4A4E4A)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Uzbek Cyrillic Legal Preview Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowest)
                        .border(1.dp, ForestGreen.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Text("РАСМИЙ КИРИЛЛ ШАКЛИ (БУЙРУҚ ИЛОВАСИ):", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = SecondaryColor)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "«$cyrillicPreview»",
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp,
                            color = WarmCharcoal,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // 5. O‘rinbosar xodim Field
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("O‘rinbosar xodim (Vazifani vaqtincha bajaruvchi)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(TertiaryFixed)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("Majburiy", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OnTertiaryFixed)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = substitutePerson,
                    onValueChange = { substitutePerson = it },
                    placeholder = { Text("O'rinbosar F.I.Sh. va lavozimi") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = ForestGreen
                    )
                )
            }
        }

        // 5b. Muddat va limit
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f)))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Muddat va Xarajat Limiti", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WarmCharcoal)
                OutlinedTextField(
                    value = datesRange,
                    onValueChange = { datesRange = it },
                    label = { Text("Muddat oralig'i") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = ForestGreen
                    )
                )
                OutlinedTextField(
                    value = expenseLimit,
                    onValueChange = { expenseLimit = it },
                    label = { Text("Maksimal xarajat limiti") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = ForestGreen
                    )
                )
            }
        }

        // 6. Important Rule Reminder Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(TerraErrorContainer.copy(alpha = 0.5f))
                .border(1.dp, TerraError.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Text("⚠️", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Diqqat: O‘z-o‘ziga ruxsat berish taqiqlanadi. Ariza Operatsion direktor yoki belgilangan vakolatli shaxs tomonidan tasdiqlanadi.",
                    fontSize = 11.sp,
                    color = WarmCharcoal,
                    lineHeight = 16.sp
                )
            }
        }

        // 7. Action Footer Buttons
        val isFormValid = reasonText.trim().length >= 20 && substitutePerson.isNotBlank()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.weight(1f).height(46.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ortga", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
            }

            Button(
                onClick = {
                    if (isFormValid) {
                        CommandCenterRepository.instance.createSopRequest(
                            category = selectedCategory,
                            reason = reasonText.trim(),
                            substitutePerson = substitutePerson.trim(),
                            datesRange = datesRange.trim(),
                            expenseLimit = expenseLimit.trim()
                        )
                        onSubmitComplete()
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.weight(2f).height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ForestGreen,
                    disabledContainerColor = SurfaceContainerHighest
                )
            ) {
                Text(
                    "Arizani yuborish",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isFormValid) OnPrimary else SecondaryColor
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("➔", fontSize = 14.sp, color = if (isFormValid) OnPrimary else SecondaryColor)
            }
        }
    }
}
