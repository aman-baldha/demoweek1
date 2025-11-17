package com.demotest.demo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demotest.demo.ui.components.AppTopBar
import com.demotest.demo.viewmodel.RealTimeDataViewModel

@Composable
fun RealTimeDataScreen(viewModel: RealTimeDataViewModel, onBack: () -> Unit) {
    val dataStream by viewModel.dataStream.collectAsState()
    val flowOperations by viewModel.flowOperations.collectAsState()
    val isStreaming by viewModel.isStreaming.collectAsState()

    Scaffold(topBar = { AppTopBar(title = "Real-Time Data", onBackClick = onBack) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Text("Data Stream", style = MaterialTheme.typography.titleMedium) }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.startDataStream() }, enabled = !isStreaming, modifier = Modifier.weight(1f)) {
                        Text("Start")
                    }
                    Button(onClick = { viewModel.stopDataStream() }, enabled = isStreaming, modifier = Modifier.weight(1f)) {
                        Text("Stop")
                    }
                }
            }

            if (dataStream.isNotEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Last 10 values:")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(dataStream.joinToString(" → "))
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Flow Operations", style = MaterialTheme.typography.titleMedium)
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.visualizeFlowOperations() }, modifier = Modifier.weight(1f)) {
                        Text("Visualize")
                    }
                    Button(onClick = { viewModel.clearOperations() }, modifier = Modifier.weight(1f)) {
                        Text("Clear")
                    }
                }
            }

            items(flowOperations) { operation ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(operation.stage, style = MaterialTheme.typography.labelMedium)
                        Text(operation.value)
                    }
                }
            }
        }
    }
}
