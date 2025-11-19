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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Week2Screen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Week 2 - Architecture") },
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
                    "Week 2: Architecture, Navigation, and UI Testing",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            
            item {
                Text(
                    "Learn Android architecture patterns and best practices",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            item {
                Text(
                    "Day 1: MVVM Basics",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            item {
                DemoItem(
                    title = "Simple MVVM Demo",
                    desc = "Counter app with ViewModel and State"
                ) {
                    onNavigate("mvvm_demo")
                }
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            item {
                Text(
                    "Day 2: MVI Pattern",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            item {
                DemoItem(
                    title = "MVI Architecture Demo",
                    desc = "Model-View-Intent with sealed class Intents"
                ) {
                    onNavigate("mvi_demo")
                }
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            item {
                Text(
                    "Coming Soon",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Day 3-7: More Content",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Navigation, Deep Links, UI Testing, and more...",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

