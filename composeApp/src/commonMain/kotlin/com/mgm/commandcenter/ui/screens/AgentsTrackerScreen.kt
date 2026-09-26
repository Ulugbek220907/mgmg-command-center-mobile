package com.mgm.commandcenter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.mgm.commandcenter.model.Agent
import com.mgm.commandcenter.model.AgentStatus
import com.mgm.commandcenter.model.UrgencyLevel
import com.mgm.commandcenter.theme.*

@Composable
fun AgentsTrackerScreen(
    agents: List<Agent>,
    onBackClick: () -> Unit,
    onUpdateAgentStatus: (String, AgentStatus) -> Boolean = { code, status ->
        CommandCenterRepository.instance.updateAgentStatus(code, status)
    }
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedBlock by remember { mutableStateOf("Hammasi") }
    var selectedUrgency by remember { mutableStateOf<UrgencyLevel?>(null) }
    var detailAgent by remember { mutableStateOf<Agent?>(null) }
    var ruleViolationMessage by remember { mutableStateOf<String?>(null) }

    val blocks = remember(agents) {
        listOf("Hammasi") + agents.map { it.block }.distinct()
    }

    val filteredAgents = remember(agents, searchQuery, selectedBlock, selectedUrgency) {
        agents.filter { agent ->
            val matchQuery = searchQuery.isBlank() ||
                    agent.code.contains(searchQuery, ignoreCase = true) ||
                    agent.name.contains(searchQuery, ignoreCase = true) ||
                    agent.assignee.contains(searchQuery, ignoreCase = true)
            val matchBlock = selectedBlock == "Hammasi" || agent.block == selectedBlock
            val matchUrgency = selectedUrgency == null || agent.urgency == selectedUrgency
            matchQuery && matchBlock && matchUrgency
        }
    }

    val activeCount = remember(agents) {
        agents.count { it.status == AgentStatus.IN_PROGRESS || it.status == AgentStatus.TESTING }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCreamBg)
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                    Text("21 AI Агентлар Трекери", style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                    Text("Асосий иш столи • ЭМЖИЕМ", fontSize = 11.sp, color = SecondaryColor)
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (activeCount > 2) TerraErrorContainer else PrimaryFixed)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Фаол: $activeCount / 2",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (activeCount > 2) TerraError else ForestGreen
                )
            }
        }

        // Golden rule banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, TerraOutlineVariant.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                .padding(8.dp)
        ) {
            Text(
                text = "⚡ Олтин қоида: Бир вақтда фақат 2 та агент қурилади. Учинчиси биттаси ТЎЛИҚ ишлаб кетмагунча бошланмайди.",
                fontSize = 10.sp,
                color = WarmAmber,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (ruleViolationMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = TerraErrorContainer),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⚠️ $ruleViolationMessage",
                        color = TerraError,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { ruleViolationMessage = null }, modifier = Modifier.size(24.dp)) {
                        Text("✕", fontSize = 12.sp, color = TerraError, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Код, ном ёки масъул бўйича қидириш...", fontSize = 12.sp) },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceContainerLowest,
                unfocusedContainerColor = SurfaceContainerLowest,
                focusedBorderColor = ForestGreen
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Block Filter Pills
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(blocks) { b ->
                val isSelected = b == selectedBlock
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) ForestGreen else SurfaceContainer)
                        .clickable { selectedBlock = b }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = b,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) OnPrimary else WarmCharcoal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Urgency Filter Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(
                null to "Ҳаммаси",
                UrgencyLevel.CRITICAL to "Критик (≥18)",
                UrgencyLevel.URGENT to "Шошилинч (≥12)",
                UrgencyLevel.NORMAL to "Оддий (≥4.8)"
            ).forEach { (urg, label) ->
                val isSelected = urg == selectedUrgency
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) SecondaryColor else SurfaceContainerLow)
                        .clickable { selectedUrgency = urg }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else SecondaryColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Agents List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredAgents) { agent ->
                AgentCardItem(
                    agent = agent,
                    onStatusChange = { newStatus ->
                        val success = onUpdateAgentStatus(agent.code, newStatus)
                        if (!success) {
                            ruleViolationMessage = "Қоида бузилиш: Бир вақтда кўпи билан 2 та агент қура оласиз!"
                        } else {
                            ruleViolationMessage = null
                        }
                    },
                    onCardClick = { detailAgent = agent }
                )
            }
        }
    }

    // Agent Detail Dialog
    detailAgent?.let { agent ->
        AlertDialog(
            onDismissRequest = { detailAgent = null },
            confirmButton = {
                Button(
                    onClick = { detailAgent = null },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Text("Ёпиш", color = OnPrimary)
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(ForestGreen)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(agent.code, color = OnPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(agent.name, style = MaterialTheme.typography.titleMedium, color = WarmCharcoal)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Блок: ${agent.block}", fontSize = 12.sp, color = SecondaryColor, fontWeight = FontWeight.SemiBold)
                    Text("Масъул: ${agent.assignee}", fontSize = 12.sp, color = ForestGreen, fontWeight = FontWeight.Bold)
                    Text("Платформа: ${agent.platform}", fontSize = 12.sp, color = WarmCharcoal)

                    Spacer(modifier = Modifier.height(4.dp))
                    Text("НИМА ҚИЛАДИ:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerraOutline)
                    Text(agent.whatItDoes, fontSize = 12.sp, color = WarmCharcoal, lineHeight = 16.sp)

                    Spacer(modifier = Modifier.height(4.dp))
                    Text("НИМА УЧУН КЕРАК (АУДИТ ДАЛИЛИ):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerraOutline)
                    Text(agent.whyNeeded, fontSize = 12.sp, color = Color(0xFF4A4E4A), lineHeight = 16.sp)

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Қуриш вақти: ${agent.buildHours} соат", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("Ойлик тежов: ${agent.monthlySavedHours} соат", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Йиллик макс: $${agent.maxAnnualProfit.toInt()}", fontSize = 11.sp, color = ForestGreen, fontWeight = FontWeight.Bold)
                        Text("Йиллик реал: $${agent.realAnnualProfit().toInt()}", fontSize = 11.sp, color = ForestGreen, fontWeight = FontWeight.Bold)
                    }
                }
            },
            containerColor = SurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun AgentCardItem(
    agent: Agent,
    onStatusChange: (AgentStatus) -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TerraOutlineVariant.copy(alpha = 0.3f))),
        modifier = Modifier.fillMaxWidth().clickable { onCardClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(ForestGreen)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(agent.code, color = OnPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = agent.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = WarmCharcoal,
                        maxLines = 1
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when (agent.urgency) {
                                UrgencyLevel.CRITICAL -> TerraErrorContainer
                                UrgencyLevel.URGENT -> SecondaryContainer
                                else -> PrimaryFixed
                            }
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${agent.urgency.titleUz} (${agent.score})",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (agent.urgency) {
                            UrgencyLevel.CRITICAL -> TerraError
                            UrgencyLevel.URGENT -> OnSecondaryContainer
                            else -> ForestGreen
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = agent.whatItDoes,
                fontSize = 11.sp,
                color = Color(0xFF4A4E4A),
                maxLines = 2,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Масъул: ", fontSize = 10.sp, color = TerraOutline)
                    Text(agent.assignee, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = WarmCharcoal)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Реал: ", fontSize = 10.sp, color = TerraOutline)
                    Text("$${agent.realAnnualProfit().toInt()}/йил", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = TerraOutlineVariant.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(6.dp))

            // Status toggler
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ҳолат:", fontSize = 10.sp, color = SecondaryColor)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    AgentStatus.entries.forEach { st ->
                        val isCurr = st == agent.status
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isCurr) ForestGreen else SurfaceContainer)
                                .clickable { onStatusChange(st) }
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = st.titleUz,
                                fontSize = 9.sp,
                                fontWeight = if (isCurr) FontWeight.Bold else FontWeight.Normal,
                                color = if (isCurr) OnPrimary else SecondaryColor
                            )
                        }
                    }
                }
            }
        }
    }
}
