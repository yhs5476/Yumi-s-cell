package com.example.careplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.careplus.data.local.AppDatabase
import com.example.careplus.data.repository.CarePlusRepository
import com.example.careplus.ui.screens.BidFeedScreen
import com.example.careplus.ui.screens.CaregiverRadarScreen
import com.example.careplus.ui.screens.ChatScreen
import com.example.careplus.ui.screens.ContractDetailScreen
import com.example.careplus.ui.screens.HomeScreen
import com.example.careplus.ui.screens.MyPageScreen
import com.example.careplus.ui.screens.RequestFormScreen
import com.example.careplus.ui.theme.CarePlusTheme
import com.example.careplus.ui.theme.TossBackground
import com.example.careplus.viewmodel.CarePlusViewModel
import com.example.careplus.viewmodel.CarePlusViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(applicationContext)
        val repository = CarePlusRepository(database.careDao())
        val viewModelFactory = CarePlusViewModelFactory(repository)

        setContent {
            CarePlusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = TossBackground
                ) {
                    val viewModel: CarePlusViewModel = viewModel(factory = viewModelFactory)
                    CarePlusApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun CarePlusApp(viewModel: CarePlusViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(280)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(280)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(280)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(280)
            )
        }
    ) {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToRequestForm = {
                    navController.navigate("request_form")
                },
                onNavigateToBidFeed = { requestId ->
                    navController.navigate("bid_feed/$requestId")
                },
                onNavigateToContracts = {
                    navController.navigate("contracts")
                },
                onNavigateToRadar = {
                    navController.navigate("caregiver_radar")
                },
                onNavigateToMyPage = {
                    navController.navigate("my_page")
                }
            )
        }

        composable("request_form") {
            RequestFormScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onComplete = { requestId ->
                    navController.navigate("bid_feed/$requestId") {
                        popUpTo("home")
                    }
                }
            )
        }

        composable(
            route = "bid_feed/{requestId}",
            arguments = listOf(navArgument("requestId") { type = NavType.LongType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getLong("requestId") ?: 1L
            BidFeedScreen(
                requestId = requestId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToChat = { bidId ->
                    navController.navigate("chat/$bidId")
                }
            )
        }

        composable(
            route = "chat/{bidId}",
            arguments = listOf(navArgument("bidId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bidId = backStackEntry.arguments?.getLong("bidId") ?: 1L
            ChatScreen(
                bidId = bidId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToContract = { contractId ->
                    navController.navigate("contracts") {
                        popUpTo("home")
                    }
                }
            )
        }

        composable("contracts") {
            ContractDetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("caregiver_radar") {
            CaregiverRadarScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("my_page") {
            MyPageScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToContracts = {
                    navController.navigate("contracts")
                }
            )
        }
    }
}
