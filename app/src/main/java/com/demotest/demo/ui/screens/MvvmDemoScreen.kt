package com.demotest.demo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.demotest.demo.viewmodel.MvvmDemoViewModel

/**
 * MVVM Demo Screen - Day 1 of Week 2
 * 
 * This demonstrates the MVVM pattern:
 * - Model: UiState data class in ViewModel
 * - View: This Composable (MvvmDemoScreen)
 * - ViewModel: MvvmDemoViewModel
 * 
 * Key concepts:
 * 1. Unidirectional data flow: State flows down, Events flow up
 * 2. Single source of truth: ViewModel holds the state
 * 3. UI is a function of state: UI = f(state)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MvvmDemoScreen(
    viewModel: MvvmDemoViewModel,
    onBack: () -> Unit
) {
    // Collect state in a lifecycle-aware manner
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // Local state for text input
    var nameInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MVVM Demo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                "MVVM Architecture Demo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                "Model-View-ViewModel pattern demonstration",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            HorizontalDivider()
            
            // Counter Section
            CounterSection(
                counter = uiState.counter,
                onIncrement = { viewModel.incrementCounter() },
                onDecrement = { viewModel.decrementCounter() },
                onReset = { viewModel.resetCounter() }
            )
            
            HorizontalDivider()
            
            // User Name Section
            UserNameSection(
                userName = uiState.userName,
                nameInput = nameInput,
                onNameInputChange = { nameInput = it },
                onUpdateName = { 
                    viewModel.updateUserName(nameInput)
                    nameInput = ""
                }
            )
            
            HorizontalDivider()
            
            // Loading State Section
            LoadingStateSection(
                isLoading = uiState.isLoading,
                onToggleLoading = { viewModel.toggleLoading() }
            )
            
            HorizontalDivider()
            
            // Explanation Card
            ExplanationCard()
        }
    }
}

@Composable
fun CounterSection(
    counter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Counter Example",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            // Display counter
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = counter.toString(),
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onDecrement) {
                    Text("-")
                }
                Button(onClick = onReset) {
                    Text("Reset")
                }
                Button(onClick = onIncrement) {
                    Text("+")
                }
            }
        }
    }
}

@Composable
fun UserNameSection(
    userName: String,
    nameInput: String,
    onNameInputChange: (String) -> Unit,
    onUpdateName: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "User Name Example",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                "Current User: $userName",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            
            OutlinedTextField(
                value = nameInput,
                onValueChange = onNameInputChange,
                label = { Text("Enter name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Button(
                onClick = onUpdateName,
                modifier = Modifier.fillMaxWidth(),
                enabled = nameInput.isNotBlank()
            ) {
                Text("Update Name")
            }
        }
    }
}

@Composable
fun LoadingStateSection(
    isLoading: Boolean,
    onToggleLoading: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Loading State Example",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp)
                )
                Text("Loading...")
            } else {
                Text(
                    "✓ Ready",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Button(
                onClick = onToggleLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLoading) "Stop Loading" else "Start Loading")
            }
        }
    }
}

@Composable
fun ExplanationCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "📚 MVVM Explained",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                "Model: UiState data class holds all UI data",
                style = MaterialTheme.typography.bodySmall
            )
            
            Text(
                "View: This screen observes state and sends events",
                style = MaterialTheme.typography.bodySmall
            )
            
            Text(
                "ViewModel: Manages state and business logic",
                style = MaterialTheme.typography.bodySmall
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            

        }
    }
}

