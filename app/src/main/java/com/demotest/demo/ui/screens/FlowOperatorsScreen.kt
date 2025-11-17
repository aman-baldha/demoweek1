package com.demotest.demo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demotest.demo.ui.components.AppTopBar
import com.demotest.demo.viewmodel.FlowOperatorsViewModel

@Composable
fun FlowOperatorsScreen(viewModel: FlowOperatorsViewModel, onBack: () -> Unit) {
    val operatorResults by viewModel.operatorResults.collectAsState()
    val isProcessing by viewModel.isProcessing.collectAsState()

    Scaffold(topBar = { AppTopBar(title = "Flow Operators", onBackClick = onBack) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.demonstrateMapOperator() }, 
                           enabled = !isProcessing, modifier = Modifier.weight(1f)) {
                        Text("map")
                    }
                    Button(onClick = { viewModel.demonstrateFilterOperator() }, 
                           enabled = !isProcessing, modifier = Modifier.weight(1f)) {
                        Text("filter")
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.demonstrateTakeOperator() }, 
                           enabled = !isProcessing, modifier = Modifier.weight(1f)) {
                        Text("take")
                    }
                    Button(onClick = { viewModel.demonstrateDropOperator() }, 
                           enabled = !isProcessing, modifier = Modifier.weight(1f)) {
                        Text("drop")
                    }
                }
            }

            item {
                Button(onClick = { viewModel.clearResults() }, 
                       enabled = !isProcessing, modifier = Modifier.fillMaxWidth()) {
                    Text("Clear")
                }
            }

            if (isProcessing) {
                item { CircularProgressIndicator() }
            }

            items(operatorResults) { result ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(result.operator, style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(result.values.joinToString(", "))
                    }
                }
            }
        }
    }
}
