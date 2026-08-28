package com.example.carematch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carematch.data.model.UserRole
import com.example.carematch.ui.components.CareMatchTopAppBar
import com.example.carematch.ui.components.NotificationPopupDialog
import com.example.carematch.ui.screens.CaregiverDetailScreen
import com.example.carematch.ui.screens.CaregiverInboxScreen
import com.example.carematch.ui.screens.CaregiverProfileEditScreen
import com.example.carematch.ui.screens.CareRequestFormScreen
import com.example.carematch.ui.screens.GuardianRequestsScreen
import com.example.carematch.ui.screens.HomeScreen
import com.example.carematch.ui.screens.NotificationCenterScreen
import com.example.carematch.ui.theme.CareMatchTheme
import com.example.carematch.ui.theme.CoralSecondary
import com.example.carematch.ui.theme.Slate600
import com.example.carematch.ui.theme.Slate900
import com.example.carematch.ui.theme.TealContainer
import com.example.carematch.ui.theme.TealDark
import com.example.carematch.ui.theme.TealPrimary
import com.example.carematch.ui.theme.White
import com.example.carematch.ui.viewmodel.CareMatchViewModel
import com.example.carematch.ui.viewmodel.CareMatchViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: CareMatchViewModel by viewModels {
        val app = application as CareMatchApplication
        CareMatchViewModelFactory(app.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CareMatchTheme {
                CareMatchApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CareMatchApp(viewModel: CareMatchViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentRole by viewModel.currentRole.collectAsStateWithLifecycle()
    val caregivers by viewModel.caregivers.collectAsStateWithLifecycle()
    val filterState by viewModel.filterState.collectAsStateWithLifecycle()
    val guardianRequests by viewModel.guardianRequests.collectAsStateWithLifecycle()
    val caregiverRequests by viewModel.caregiverRequests.collectAsStateWithLifecycle()
    val selectedCaregiver by viewModel.selectedCaregiver.collectAsStateWithLifecycle()
    val selectedReviews by viewModel.selectedCaregiverReviews.collectAsStateWithLifecycle()
    val myCaregiverProfile by viewModel.myCaregiverProfile.collectAsStateWithLifecycle()
    val requestForm by viewModel.requestForm.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val unreadNotiCount by viewModel.unreadNotificationCount.collectAsStateWithLifecycle()

    val showNotificationPopup by viewModel.showNotificationDialog.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isTopLevelRoute = currentRoute in listOf(
        "guardian_home",
        "guardian_requests",
        "guardian_notifications",
        "caregiver_inbox",
        "caregiver_profile",
        "caregiver_notifications"
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (isTopLevelRoute) {
                CareMatchTopAppBar(
                    currentRole = currentRole,
                    unreadNotificationCount = unreadNotiCount,
                    onSwitchRole = {
                        val newRole = if (currentRole == UserRole.GUARDIAN) UserRole.CAREGIVER else UserRole.GUARDIAN
                        viewModel.switchRole(newRole)
                        val targetRoute = if (newRole == UserRole.GUARDIAN) "guardian_home" else "caregiver_inbox"
                        navController.navigate(targetRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNotificationClick = {
                        viewModel.showNotificationDialog.value = true
                    }
                )
            }
        },
        bottomBar = {
            if (isTopLevelRoute) {
                Surface(
                    color = White,
                    shadowElevation = 8.dp
                ) {
                    NavigationBar(
                        containerColor = White,
                        tonalElevation = 0.dp
                    ) {
                        if (currentRole == UserRole.GUARDIAN) {
                            // Guardian Tabs
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == "guardian_home") Icons.Filled.Search else Icons.Outlined.Search,
                                        contentDescription = "간병인 찾기"
                                    )
                                },
                                label = { Text("간병인 찾기", fontSize = 11.sp, fontWeight = if (currentRoute == "guardian_home") FontWeight.Bold else FontWeight.Normal) },
                                selected = currentRoute == "guardian_home",
                                onClick = {
                                    navController.navigate("guardian_home") {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = TealDark,
                                    selectedTextColor = TealDark,
                                    indicatorColor = TealContainer
                                ),
                                modifier = Modifier.testTag("nav_guardian_home")
                            )

                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == "guardian_requests") Icons.Filled.Assignment else Icons.Outlined.Assignment,
                                        contentDescription = "신청 내역"
                                    )
                                },
                                label = { Text("신청 내역", fontSize = 11.sp, fontWeight = if (currentRoute == "guardian_requests") FontWeight.Bold else FontWeight.Normal) },
                                selected = currentRoute == "guardian_requests",
                                onClick = {
                                    navController.navigate("guardian_requests") {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = TealDark,
                                    selectedTextColor = TealDark,
                                    indicatorColor = TealContainer
                                ),
                                modifier = Modifier.testTag("nav_guardian_requests")
                            )

                            NavigationBarItem(
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            if (unreadNotiCount > 0) {
                                                Badge(containerColor = CoralSecondary, contentColor = White) {
                                                    Text("$unreadNotiCount")
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (currentRoute == "guardian_notifications") Icons.Filled.Notifications else Icons.Outlined.Notifications,
                                            contentDescription = "알림함"
                                        )
                                    }
                                },
                                label = { Text("알림함", fontSize = 11.sp, fontWeight = if (currentRoute == "guardian_notifications") FontWeight.Bold else FontWeight.Normal) },
                                selected = currentRoute == "guardian_notifications",
                                onClick = {
                                    navController.navigate("guardian_notifications") {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = TealDark,
                                    selectedTextColor = TealDark,
                                    indicatorColor = TealContainer
                                ),
                                modifier = Modifier.testTag("nav_guardian_notifications")
                            )
                        } else {
                            // Caregiver Tabs
                            NavigationBarItem(
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            val pendingCount = caregiverRequests.count { it.status == com.example.carematch.data.model.RequestStatus.PENDING }
                                            if (pendingCount > 0) {
                                                Badge(containerColor = CoralSecondary, contentColor = White) {
                                                    Text("$pendingCount")
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (currentRoute == "caregiver_inbox") Icons.Filled.Inbox else Icons.Outlined.Inbox,
                                            contentDescription = "받은 요청"
                                        )
                                    }
                                },
                                label = { Text("받은 요청", fontSize = 11.sp, fontWeight = if (currentRoute == "caregiver_inbox") FontWeight.Bold else FontWeight.Normal) },
                                selected = currentRoute == "caregiver_inbox",
                                onClick = {
                                    navController.navigate("caregiver_inbox") {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = TealDark,
                                    selectedTextColor = TealDark,
                                    indicatorColor = TealContainer
                                ),
                                modifier = Modifier.testTag("nav_caregiver_inbox")
                            )

                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == "caregiver_profile") Icons.Filled.Person else Icons.Outlined.Person,
                                        contentDescription = "프로필 관리"
                                    )
                                },
                                label = { Text("프로필 관리", fontSize = 11.sp, fontWeight = if (currentRoute == "caregiver_profile") FontWeight.Bold else FontWeight.Normal) },
                                selected = currentRoute == "caregiver_profile",
                                onClick = {
                                    navController.navigate("caregiver_profile") {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = TealDark,
                                    selectedTextColor = TealDark,
                                    indicatorColor = TealContainer
                                ),
                                modifier = Modifier.testTag("nav_caregiver_profile")
                            )

                            NavigationBarItem(
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            if (unreadNotiCount > 0) {
                                                Badge(containerColor = CoralSecondary, contentColor = White) {
                                                    Text("$unreadNotiCount")
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (currentRoute == "caregiver_notifications") Icons.Filled.Notifications else Icons.Outlined.Notifications,
                                            contentDescription = "알림함"
                                        )
                                    }
                                },
                                label = { Text("알림함", fontSize = 11.sp, fontWeight = if (currentRoute == "caregiver_notifications") FontWeight.Bold else FontWeight.Normal) },
                                selected = currentRoute == "caregiver_notifications",
                                onClick = {
                                    navController.navigate("caregiver_notifications") {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = TealDark,
                                    selectedTextColor = TealDark,
                                    indicatorColor = TealContainer
                                ),
                                modifier = Modifier.testTag("nav_caregiver_notifications")
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "guardian_home",
            modifier = Modifier.padding(paddingValues)
        ) {
            // Guardian Home
            composable("guardian_home") {
                HomeScreen(
                    caregivers = caregivers,
                    filterState = filterState,
                    onSearchChange = viewModel::updateSearchQuery,
                    onRegionSelect = viewModel::setRegion,
                    onSpecialtyToggle = viewModel::toggleSpecialty,
                    onCertificationToggle = viewModel::toggleCertification,
                    onMaxDailyPayChange = viewModel::setMaxDailyPay,
                    onSortOptionChange = viewModel::setSortOption,
                    onResetFilters = viewModel::resetFilters,
                    onCaregiverClick = { caregiverId ->
                        viewModel.selectCaregiver(caregiverId)
                        navController.navigate("caregiver_detail/$caregiverId")
                    },
                    onRequestClick = { caregiverId ->
                        viewModel.selectCaregiver(caregiverId)
                        navController.navigate("care_request_form/$caregiverId")
                    }
                )
            }

            // Caregiver Detail Screen
            composable(
                route = "caregiver_detail/{caregiverId}",
                arguments = listOf(navArgument("caregiverId") { type = NavType.LongType })
            ) {
                CaregiverDetailScreen(
                    caregiver = selectedCaregiver,
                    reviews = selectedReviews,
                    onBack = { navController.popBackStack() },
                    onRequestCare = { caregiverId ->
                        navController.navigate("care_request_form/$caregiverId")
                    }
                )
            }

            // Care Request Form Screen
            composable(
                route = "care_request_form/{caregiverId}",
                arguments = listOf(navArgument("caregiverId") { type = NavType.LongType })
            ) {
                selectedCaregiver?.let { caregiver ->
                    CareRequestFormScreen(
                        caregiver = caregiver,
                        formState = requestForm,
                        onFormUpdate = viewModel::updateRequestForm,
                        onSubmit = {
                            viewModel.submitCareRequest(caregiver) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("간병 신청서가 성공적으로 제출되었습니다! 알림톡이 전송되었습니다.")
                                }
                                navController.navigate("guardian_requests") {
                                    popUpTo("guardian_home")
                                }
                            }
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
            }

            // Guardian Requests History
            composable("guardian_requests") {
                GuardianRequestsScreen(
                    requests = guardianRequests,
                    onExploreClick = {
                        navController.navigate("guardian_home") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            // Guardian Notifications
            composable("guardian_notifications") {
                NotificationCenterScreen(
                    notifications = notifications,
                    onMarkAllAsRead = viewModel::markAllNotificationsAsRead,
                    onNotificationClick = {
                        viewModel.markNotificationAsRead(it.id)
                    }
                )
            }

            // Caregiver Inbox
            composable("caregiver_inbox") {
                CaregiverInboxScreen(
                    requests = caregiverRequests,
                    onAccept = { requestId ->
                        viewModel.acceptRequest(requestId)
                        scope.launch {
                            snackbarHostState.showSnackbar("요청을 수락하였습니다. 보호자에게 알림톡이 발송되었습니다.")
                        }
                    },
                    onReject = { requestId, reason ->
                        viewModel.rejectRequest(requestId, reason)
                        scope.launch {
                            snackbarHostState.showSnackbar("요청이 거절되었습니다.")
                        }
                    }
                )
            }

            // Caregiver Profile Editor
            composable("caregiver_profile") {
                CaregiverProfileEditScreen(
                    profile = myCaregiverProfile,
                    onSaveProfile = viewModel::updateMyCaregiverProfile
                )
            }

            // Caregiver Notifications
            composable("caregiver_notifications") {
                NotificationCenterScreen(
                    notifications = notifications,
                    onMarkAllAsRead = viewModel::markAllNotificationsAsRead,
                    onNotificationClick = {
                        viewModel.markNotificationAsRead(it.id)
                    }
                )
            }
        }
    }

    if (showNotificationPopup) {
        NotificationPopupDialog(
            notifications = notifications,
            onDismiss = { viewModel.showNotificationDialog.value = false },
            onMarkAllRead = { viewModel.markAllNotificationsAsRead() },
            onViewFullCenter = {
                val target = if (currentRole == UserRole.GUARDIAN) "guardian_notifications" else "caregiver_notifications"
                navController.navigate(target) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
