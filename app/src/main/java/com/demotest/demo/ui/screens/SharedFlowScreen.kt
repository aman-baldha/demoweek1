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
import com.demotest.demo.viewmodel.SharedFlowViewModel
import com.demotest.demo.viewmodel.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SharedFlowScreen(viewModel: SharedFlowViewModel, onBack: () -> Unit) {
    val collectedMessages by viewModel.collectedMessages.collectAsState()
    var messageText by remember { mutableStateOf("") }

    Scaffold(topBar = { AppTopBar(title = "SharedFlow", onBackClick = onBack) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Broadcasting Messages", style = MaterialTheme.typography.titleMedium)
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    label = { Text("Type message") },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    if (messageText.isNotEmpty()) {
                        viewModel.broadcastMessage(messageText)
                        messageText = ""
                    }
                }) {
                    Text("Send")
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Messages (${collectedMessages.size})")
                TextButton(onClick = { viewModel.clearMessages() }) { Text("Clear") }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(collectedMessages.takeLast(10)) { message ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(message, modifier = Modifier.padding(12.dp))
                    }
                }
            }
        }
    }
}
