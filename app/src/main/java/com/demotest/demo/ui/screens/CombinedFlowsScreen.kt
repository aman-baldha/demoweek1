package com.demotest.demo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demotest.demo.ui.components.AppTopBar
import com.demotest.demo.viewmodel.CombinedFlowsViewModel

@Composable
fun CombinedFlowsScreen(viewModel: CombinedFlowsViewModel, onBack: () -> Unit) {
    val results by viewModel.results.collectAsState()
    val isProcessing by viewModel.isProcessing.collectAsState()

    Scaffold(topBar = { AppTopBar(title = "Combined Flows", onBackClick = onBack) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.demonstrateCombine() }, enabled = !isProcessing, modifier = Modifier.weight(1f)) {
                        Text("combine")
                    }
                    Button(onClick = { viewModel.demonstrateZip() }, enabled = !isProcessing, modifier = Modifier.weight(1f)) {
                        Text("zip")
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.demonstrateMerge() }, enabled = !isProcessing, modifier = Modifier.weight(1f)) {
                        Text("merge")
                    }
                    Button(onClick = { viewModel.demonstrateFlatMapLatest() }, enabled = !isProcessing, modifier = Modifier.weight(1f)) {
                        Text("flatMapLatest")
                    }
                }
            }

            item {
                Button(onClick = { viewModel.clearResults() }, enabled = !isProcessing, modifier = Modifier.fillMaxWidth()) {
                    Text("Clear")
                }
            }

            if (isProcessing) {
                item { CircularProgressIndicator() }
            }

            items(results) { result ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(result.type.uppercase(), style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        result.values.forEach { value ->
                            Text("• $value", modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

