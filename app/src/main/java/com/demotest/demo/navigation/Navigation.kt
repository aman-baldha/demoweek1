package com.demotest.demo.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.demotest.demo.ui.screens.*
import com.demotest.demo.viewmodel.*

sealed class Screen(val route: String, val title: String) {
    object WeekSelection : Screen("week_selection", "Week Selection")
    object Week1 : Screen("week_1", "Week 1")
    object Week2 : Screen("week_2", "Week 2")
    object Week3 : Screen("week_3", "Week 3")
    object Week4 : Screen("week_4", "Week 4")
    object Week5 : Screen("week_5", "Week 5")
    object Week6 : Screen("week_6", "Week 6")
    object MvvmDemo : Screen("mvvm_demo", "MVVM Demo")
    object MviDemo : Screen("mvi_demo", "MVI Demo")
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
        startDestination = Screen.WeekSelection.route
    ) {
        composable(Screen.WeekSelection.route) {
            WeekSelectionScreen(
                onWeekSelected = { weekNumber ->
                    when (weekNumber) {
                        1 -> navController.navigate(Screen.Week1.route)
                        2 -> navController.navigate(Screen.Week2.route)
                        3 -> navController.navigate(Screen.Week3.route)
                        4 -> navController.navigate(Screen.Week4.route)
                        5 -> navController.navigate(Screen.Week5.route)
                        6 -> navController.navigate(Screen.Week6.route)
                    }
                }
            )
        }
        
        composable(Screen.Week1.route) {
            Week1Screen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Week2.route) {
            Week2Screen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.MvvmDemo.route) {
            val viewModel: MvvmDemoViewModel = viewModel()
            MvvmDemoScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.MviDemo.route) {
            val viewModel: MviDemoViewModel = viewModel()
            MviDemoScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Week3.route) {
            ComingSoonScreen(
                weekNumber = 3,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Week4.route) {
            ComingSoonScreen(
                weekNumber = 4,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Week5.route) {
            ComingSoonScreen(
                weekNumber = 5,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Week6.route) {
            ComingSoonScreen(
                weekNumber = 6,
                onBack = { navController.popBackStack() }
            )
        }
        
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

