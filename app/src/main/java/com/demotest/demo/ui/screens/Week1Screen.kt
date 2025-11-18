package com.demotest.demo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demotest.demo.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Week1Screen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Week 1 - Flow Demos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Week 1: Kotlin Flow Basics",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            
            item {
                Text(
                    "Learn the fundamentals of Kotlin Flow for reactive programming",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            item {
                DemoItem(
                    title = "Basic Flow",
                    desc = "Cold flow, emission, collection"
                ) {
                    onNavigate(Screen.BasicFlow.route)
                }
            }
            
            item {
                DemoItem(
                    title = "Flow Operators",
                    desc = "map, filter, transform, etc"
                ) {
                    onNavigate(Screen.FlowOperators.route)
                }
            }
            
            item {
                DemoItem(
                    title = "StateFlow",
                    desc = "UI state management"
                ) {
                    onNavigate(Screen.StateFlowDemo.route)
                }
            }
            
            item {
                DemoItem(
                    title = "SharedFlow",
                    desc = "Events & broadcasting"
                ) {
                    onNavigate(Screen.SharedFlowDemo.route)
                }
            }
            
            item {
                DemoItem(
                    title = "Combined Flows",
                    desc = "combine, zip, merge"
                ) {
                    onNavigate(Screen.CombinedFlows.route)
                }
            }
            
            item {
                DemoItem(
                    title = "Real-Time Data",
                    desc = "Live streams"
                ) {
                    onNavigate(Screen.RealTimeData.route)
                }
            }
        }
    }
}

