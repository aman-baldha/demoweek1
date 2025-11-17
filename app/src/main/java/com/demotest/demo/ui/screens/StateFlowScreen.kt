 package com.demotest.demo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demotest.demo.data.models.NetworkResult
import com.demotest.demo.ui.components.AppTopBar
import com.demotest.demo.viewmodel.StateFlowViewModel

@Composable
fun StateFlowScreen(viewModel: StateFlowViewModel, onBack: () -> Unit) {
    val counterState by viewModel.counterState.collectAsStateWithLifecycle()
    val firstName by viewModel.firstName.collectAsStateWithLifecycle()
    val lastName by viewModel.lastName.collectAsStateWithLifecycle()
    val fullName by viewModel.fullName.collectAsStateWithLifecycle()

    Scaffold(topBar = { AppTopBar(title = "StateFlow", onBackClick = onBack) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Text("Counter", style = MaterialTheme.typography.titleMedium) }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("${counterState.count}", style = MaterialTheme.typography.displayLarge)
                        Text(if (counterState.isEven) "Even" else "Odd")
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.decrementCounter() }, modifier = Modifier.weight(1f)) { Text("-") }
                    Button(onClick = { viewModel.resetCounter() }, modifier = Modifier.weight(1f)) { Text("Reset") }
                    Button(onClick = { viewModel.incrementCounter() }, modifier = Modifier.weight(1f)) { Text("+") }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Combined Flows", style = MaterialTheme.typography.titleMedium)
            }

            item {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { viewModel.updateFirstName(it) },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { viewModel.updateLastName(it) },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (fullName.isNotEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text("Full Name: $fullName", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

