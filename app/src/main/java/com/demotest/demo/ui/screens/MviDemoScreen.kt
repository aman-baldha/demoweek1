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
import com.demotest.demo.viewmodel.MviDemoViewModel

/**
 * MVI Demo Screen - Day 2 of Week 2
 * 
 * MVI (Model-View-Intent) Pattern:
 * - Model: UiState (single immutable state)
 * - View: This Composable (renders state)
 * - Intent: UserIntent (all possible user actions)
 * 
 * Key difference from MVVM:
 * - All actions are modeled as explicit Intents
 * - Single processIntent() function handles all actions
 * - More predictable and testable state changes
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MviDemoScreen(
    viewModel: MviDemoViewModel,
    onBack: () -> Unit
) {
    // Collect state - View renders based on state
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MVI Demo") },
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
                "MVI Architecture Demo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                "Model-View-Intent pattern demonstration",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Last Action Indicator
            LastActionCard(lastAction = state.lastAction)
            
            HorizontalDivider()
            
            // User Loading Section
            MviUserSection(
                userName = state.userName,
                userEmail = state.userEmail,
                isLoading = state.isLoading,
                error = state.error,
                onIntent = { viewModel.processIntent(it) }
            )
            
            HorizontalDivider()
            
            // Text Input Section
            MviTextSection(
                textInput = state.textInput,
                onIntent = { viewModel.processIntent(it) }
            )
            
            HorizontalDivider()
            
            // Refresh Section
            MviRefreshSection(
                isLoading = state.isLoading,
                onIntent = { viewModel.processIntent(it) }
            )
            
            HorizontalDivider()
            
            // Explanation
            MviExplanationCard()
        }
    }
}

@Composable
fun LastActionCard(lastAction: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Last Intent: ",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                lastAction,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MviUserSection(
    userName: String,
    userEmail: String,
    isLoading: Boolean,
    error: String?,
    onIntent: (MviDemoViewModel.UserIntent) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Async Operations with MVI",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            if (isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Text("Loading user data...")
                }
            } else if (error != null) {
                Text(
                    "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "Name: $userName",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "Email: $userEmail",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Text(
                "Load a user:",
                style = MaterialTheme.typography.labelMedium
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onIntent(MviDemoViewModel.UserIntent.LoadUser(1)) },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
                ) {
                    Text("User 1")
                }
                Button(
                    onClick = { onIntent(MviDemoViewModel.UserIntent.LoadUser(2)) },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
                ) {
                    Text("User 2")
                }
                Button(
                    onClick = { onIntent(MviDemoViewModel.UserIntent.LoadUser(3)) },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
                ) {
                    Text("User 3")
                }
            }
        }
    }
}

@Composable
fun MviTextSection(
    textInput: String,
    onIntent: (MviDemoViewModel.UserIntent) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Text Input with Intent",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            OutlinedTextField(
                value = textInput,
                onValueChange = { onIntent(MviDemoViewModel.UserIntent.UpdateText(it)) },
                label = { Text("Type something") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    Text("Current value: ${textInput.ifBlank { "(empty)" }}")
                }
            )
        }
    }
}

@Composable
fun MviRefreshSection(
    isLoading: Boolean,
    onIntent: (MviDemoViewModel.UserIntent) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Refresh Data",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Button(
                onClick = { onIntent(MviDemoViewModel.UserIntent.RefreshData) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                Text("Refresh")
            }
        }
    }
}

@Composable
fun MviExplanationCard() {
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
                "📚 MVI Pattern Explained",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                "Model: Single UiState representing entire UI",
                style = MaterialTheme.typography.bodySmall
            )
            
            Text(
                "View: Renders state and emits Intents",
                style = MaterialTheme.typography.bodySmall
            )
            
            Text(
                "Intent: Sealed class of all user actions",
                style = MaterialTheme.typography.bodySmall
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "🔄 MVI Flow:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                "View → Intent → ViewModel → State → View",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "✨ Benefits:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                "• More predictable state changes",
                style = MaterialTheme.typography.bodySmall
            )
            
            Text(
                "• Easier to test and debug",
                style = MaterialTheme.typography.bodySmall
            )
            
            Text(
                "• Clear action traceability",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

