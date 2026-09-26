package com.mgm.commandcenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mgm.commandcenter.data.CommandCenterRepository
import com.mgm.commandcenter.model.DirectorOutcome
import com.mgm.commandcenter.theme.TerraTheme
import com.mgm.commandcenter.theme.WarmCreamBg
import com.mgm.commandcenter.ui.components.AppTab
import com.mgm.commandcenter.ui.components.PrimusBottomNavBar
import com.mgm.commandcenter.ui.components.PrimusTopAppBar
import com.mgm.commandcenter.ui.screens.*

enum class ActiveScreen {
    MAIN_TABS,
    WRITE_REPORT,
    NEW_SOP,
    SOP_DETAIL,
    AGENTS_TRACKER,
    EXECUTIVE_DASHBOARD
}

@Composable
fun App(
    repository: CommandCenterRepository = CommandCenterRepository.instance
) {
    TerraTheme {
        val isLoggedIn by repository.isLoggedIn.collectAsState()
        val userProfile by repository.userProfile.collectAsState()
        val dailyReport by repository.dailyReport.collectAsState()
        val reportHistory by repository.reportHistory.collectAsState()
        val currentSop by repository.currentSopRequest.collectAsState()
        val agentsList by repository.agents.collectAsState()
        val executiveNumbers by repository.executiveNumbers.collectAsState()
        val financialHealth by repository.financialHealth.collectAsState()

        var currentTab by remember { mutableStateOf(AppTab.HOME) }
        var activeScreen by remember { mutableStateOf(ActiveScreen.MAIN_TABS) }
        var isDecisionModalOpen by remember { mutableStateOf(false) }
        var decisionOutcomeToReview by remember { mutableStateOf(DirectorOutcome.CONDITIONAL) }

        if (!isLoggedIn) {
            LoginScreen(
                onLoginSuccess = { code ->
                    repository.login(code)
                }
            )
        } else {
            Scaffold(
                topBar = {
                    if (activeScreen == ActiveScreen.MAIN_TABS) {
                        PrimusTopAppBar(
                            title = when (currentTab) {
                                AppTab.HOME -> "Primus Command"
                                AppTab.REPORT -> "Kunlik hisobot"
                                AppTab.PERMISSIONS -> "Ruxsatnomalar (SOP)"
                                AppTab.SETTINGS -> "Sozlamalar va Profil"
                            },
                            badgeText = userProfile.roleTitle.take(9),
                            codeText = userProfile.employeeCode,
                            userInitials = userProfile.initials
                        )
                    }
                },
                bottomBar = {
                    if (activeScreen == ActiveScreen.MAIN_TABS) {
                        PrimusBottomNavBar(
                            selectedTab = currentTab,
                            onTabSelected = { tab ->
                                currentTab = tab
                            }
                        )
                    }
                },
                containerColor = WarmCreamBg
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(WarmCreamBg)
                ) {
                    when (activeScreen) {
                        ActiveScreen.MAIN_TABS -> {
                            when (currentTab) {
                                AppTab.HOME -> HomeScreen(
                                    dailyReport = dailyReport,
                                    latestSop = currentSop,
                                    onNavigateToWriteReport = { activeScreen = ActiveScreen.WRITE_REPORT },
                                    onNavigateToNewSop = { activeScreen = ActiveScreen.NEW_SOP },
                                    onNavigateToSopDetail = { activeScreen = ActiveScreen.SOP_DETAIL },
                                    onOpenAgentsTracker = { activeScreen = ActiveScreen.AGENTS_TRACKER },
                                    onOpenExecutiveDashboard = { activeScreen = ActiveScreen.EXECUTIVE_DASHBOARD }
                                )
                                AppTab.REPORT -> DailyReportScreen(
                                    report = dailyReport,
                                    historyList = reportHistory,
                                    onSubmitReport = { text, answer ->
                                        repository.submitDailyReport(text, answer)
                                    },
                                    onBackClick = { currentTab = AppTab.HOME }
                                )
                                AppTab.PERMISSIONS -> SopDetailScreen(
                                    sop = currentSop,
                                    onBackClick = { currentTab = AppTab.HOME },
                                    onOpenDecisionModal = { outcome ->
                                        decisionOutcomeToReview = outcome
                                        isDecisionModalOpen = true
                                    },
                                    onNavigateToNewSop = { activeScreen = ActiveScreen.NEW_SOP }
                                )
                                AppTab.SETTINGS -> SettingsScreen(
                                    profile = userProfile,
                                    onLogoutClick = { repository.logout() }
                                )
                            }
                        }

                        ActiveScreen.WRITE_REPORT -> DailyReportScreen(
                            report = dailyReport,
                            historyList = reportHistory,
                            onSubmitReport = { text, answer ->
                                val ok = repository.submitDailyReport(text, answer)
                                if (ok) {
                                    activeScreen = ActiveScreen.MAIN_TABS
                                    currentTab = AppTab.REPORT
                                }
                                ok
                            },
                            onBackClick = { activeScreen = ActiveScreen.MAIN_TABS }
                        )

                        ActiveScreen.NEW_SOP -> SopRequestScreen(
                            onBackClick = { activeScreen = ActiveScreen.MAIN_TABS },
                            onSubmitComplete = {
                                activeScreen = ActiveScreen.SOP_DETAIL
                            }
                        )

                        ActiveScreen.SOP_DETAIL -> SopDetailScreen(
                            sop = currentSop,
                            onBackClick = { activeScreen = ActiveScreen.MAIN_TABS },
                            onOpenDecisionModal = { outcome ->
                                decisionOutcomeToReview = outcome
                                isDecisionModalOpen = true
                            },
                            onNavigateToNewSop = { activeScreen = ActiveScreen.NEW_SOP }
                        )

                        ActiveScreen.AGENTS_TRACKER -> AgentsTrackerScreen(
                            agents = agentsList,
                            onBackClick = { activeScreen = ActiveScreen.MAIN_TABS },
                            onUpdateAgentStatus = { code, status -> repository.updateAgentStatus(code, status) }
                        )

                        ActiveScreen.EXECUTIVE_DASHBOARD -> DashboardScreen(
                            numbers = executiveNumbers,
                            financial = financialHealth,
                            onBackClick = { activeScreen = ActiveScreen.MAIN_TABS },
                            onSetRealizationCoeff = { repository.setRealizationCoefficient(it) },
                            onToggleHaltConditions = { rising, dropping -> repository.updateOrderHaltConditions(rising, dropping) }
                        )
                    }

                    if (isDecisionModalOpen) {
                        DirectorDecisionModal(
                            initialOutcome = decisionOutcomeToReview,
                            onDismiss = { isDecisionModalOpen = false },
                            onConfirmDecision = { outcome, condition ->
                                repository.recordDirectorDecision(outcome, condition)
                                isDecisionModalOpen = false
                            }
                        )
                    }
                }
            }
        }
    }
}
