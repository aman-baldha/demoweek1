package com.demotest.demo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demotest.demo.navigation.Screen
import com.demotest.demo.ui.components.AppTopBar

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Scaffold(topBar = { AppTopBar(title = "Flow Demos") }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Text("Flow Examples", style = MaterialTheme.typography.headlineSmall) }
            
            item {
                DemoItem("Basic Flow", "Cold flow, emission, collection") {
                    onNavigate(Screen.BasicFlow.route)
                }
            }
            item {
                DemoItem("Flow Operators", "map, filter, transform, etc") {
                    onNavigate(Screen.FlowOperators.route)
                }
            }
            item {
                DemoItem("StateFlow", "UI state management") {
                    onNavigate(Screen.StateFlowDemo.route)
                }
            }
            item {
                DemoItem("SharedFlow", "Events & broadcasting") {
                    onNavigate(Screen.SharedFlowDemo.route)
                }
            }
            item {
                DemoItem("Combined Flows", "combine, zip, merge") {
                    onNavigate(Screen.CombinedFlows.route)
                }
            }
            item {
                DemoItem("Real-Time Data", "Live streams") {
                    onNavigate(Screen.RealTimeData.route)
                }
            }
        }
    }
}

@Composable
fun DemoItem(title: String, desc: String, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, style = MaterialTheme.typography.bodySmall)
        }
    }
}

