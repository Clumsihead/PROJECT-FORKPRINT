package com.forkprint.app.ui.timeline

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.ui.ForkprintViewModel
import com.forkprint.app.ui.components.VisitCard
import com.forkprint.app.ui.location.LocationPermissionPrompt
import com.forkprint.app.ui.util.formatTimelineMonth

@Composable
fun TimelineScreen(
    modifier: Modifier = Modifier,
    viewModel: ForkprintViewModel,
    onVisitClick: (String) -> Unit = {},
) {
    val visits by viewModel.timeline.collectAsState()
    var isAddingVisit by remember { mutableStateOf(false) }

    if (isAddingVisit) {
        AddVisitDialog(
            onDismiss = { isAddingVisit = false },
            onSave = { draft ->
                viewModel.addManualVisit(draft)
                isAddingVisit = false
            },
        )
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { isAddingVisit = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add memory") },
            )
        },
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Your food memories", style = MaterialTheme.typography.headlineMedium)
            Text("Private, local, and centered on visits.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            LocationPermissionPrompt(onPermissionGranted = viewModel::startVisitDetection)
            AnimatedVisibility(visits.isEmpty()) {
                EmptyTimelineCard()
            }
            AnimatedVisibility(visits.isNotEmpty()) {
                TimelineList(visits = visits, onVisitClick = onVisitClick)
            }
        }
    }
}

@Composable
private fun EmptyTimelineCard() {
    Card(Modifier.fillMaxWidth().padding(top = 24.dp)) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("No food memories yet", style = MaterialTheme.typography.titleLarge)
            Text(
                "Add a first memory manually, or enable quiet visit detection. Forkprint remains useful either way.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Composable
private fun TimelineList(visits: List<Visit>, onVisitClick: (String) -> Unit) {
    val grouped = visits.groupBy { formatTimelineMonth(it.startedAt) }
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        grouped.forEach { (month, monthVisits) ->
            item(key = month) {
                Text(month, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(top = 8.dp))
            }
            items(monthVisits, key = { it.id }) { visit -> VisitCard(visit, onClick = { onVisitClick(visit.id) }) }
        }
    }
}
