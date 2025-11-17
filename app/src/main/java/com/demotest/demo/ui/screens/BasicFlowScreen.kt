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
import com.demotest.demo.viewmodel.BasicFlowViewModel

@Composable
fun BasicFlowScreen(viewModel: BasicFlowViewModel, onBack: () -> Unit) {
    val flowItems by viewModel.flowItems.collectAsState()
    val isCollecting by viewModel.isCollecting.collectAsState()
    val coldFlowResults by viewModel.coldFlowResults.collectAsState()

    Scaffold(topBar = { AppTopBar(title = "Basic Flow", onBackClick = onBack) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { viewModel.startBasicFlow() },
                        enabled = !isCollecting,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Start")
                    }
                    Button(
                        onClick = { viewModel.clearResults() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Clear")
                    }
                }
            }

            if (isCollecting) {
                item { CircularProgressIndicator() }
            }

            items(flowItems) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.value)
                        Text("#${item.id}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            item { 
                Spacer(modifier = Modifier.height(16.dp))
                Text("Cold Flow Demo", style = MaterialTheme.typography.titleMedium)
            }

            item {
                Button(
                    onClick = { viewModel.demonstrateColdFlow() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Run Cold Flow (2 Collectors)")
                }
            }

            items(coldFlowResults) { result ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(result, modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}

