package com.demotest.demo.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.demotest.demo.ui.screens.*
import com.demotest.demo.viewmodel.*

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Flow Demo Hub")
    object BasicFlow : Screen("basic_flow", "Basic Flow")
    object FlowOperators : Screen("flow_operators", "Flow Operators")
    object StateFlowDemo : Screen("state_flow", "StateFlow")
    object SharedFlowDemo : Screen("shared_flow", "SharedFlow")
    object CombinedFlows : Screen("combined_flows", "Combined Flows")
    object RealTimeData : Screen("real_time_data", "Real-Time Data")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        
        composable(Screen.BasicFlow.route) {
            val viewModel: BasicFlowViewModel = viewModel()
            BasicFlowScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.FlowOperators.route) {
            val viewModel: FlowOperatorsViewModel = viewModel()
            FlowOperatorsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.StateFlowDemo.route) {
            val viewModel: StateFlowViewModel = viewModel()
            StateFlowScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.SharedFlowDemo.route) {
            val viewModel: SharedFlowViewModel = viewModel()
            SharedFlowScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.CombinedFlows.route) {
            val viewModel: CombinedFlowsViewModel = viewModel()
            CombinedFlowsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.RealTimeData.route) {
            val viewModel: RealTimeDataViewModel = viewModel()
            RealTimeDataScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

