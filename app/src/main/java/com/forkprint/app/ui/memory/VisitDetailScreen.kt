package com.forkprint.app.ui.memory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.ui.ForkprintViewModel
import com.forkprint.app.ui.util.formatDuration
import com.forkprint.app.ui.util.formatMemoryDate
import com.forkprint.app.ui.util.formatMemoryTime
import com.forkprint.app.ui.util.sourceLabel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun VisitDetailScreen(
    visit: Visit,
    timelinePosition: Int,
    previousVisitCount: Int,
    futureVisitCount: Int,
    viewModel: ForkprintViewModel,
    onBack: () -> Unit,
    onRestaurantClick: (String) -> Unit,
) {
    var editing by remember(visit.id) { mutableStateOf(false) }
    var confirmingDelete by remember(visit.id) { mutableStateOf(false) }

    if (editing) {
        EditVisitDialog(
            visit = visit,
            onDismiss = { editing = false },
            onSave = { update ->
                viewModel.updateVisit(visit.id, update)
                editing = false
            },
        )
    }
    if (confirmingDelete) {
        AlertDialog(
            onDismissRequest = { confirmingDelete = false },
            title = { Text("Delete this memory?") },
            text = { Text("This removes the visit from your local journal. The restaurant can remain through other visits.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteVisit(visit.id)
                    confirmingDelete = false
                    onBack()
                }) { Text("Delete") }
            },
            dismissButton = { TextButton(onClick = { confirmingDelete = false }) { Text("Cancel") } },
        )
    }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Memory") },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") } },
            actions = {
                IconButton(onClick = { editing = true }) { Icon(Icons.Default.Edit, contentDescription = "Edit memory") }
                IconButton(onClick = { confirmingDelete = true }) { Icon(Icons.Default.Delete, contentDescription = "Delete memory") }
            },
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(formatMemoryDate(visit.startedAt), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
                    Text(visit.restaurant.name, style = MaterialTheme.typography.displaySmall)
                    visit.note.takeIf { it.isNotBlank() }?.let {
                        Text("“$it”", style = MaterialTheme.typography.headlineSmall, fontStyle = FontStyle.Italic)
                    } ?: Text("No note yet. Add what made this meal worth remembering.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                }
            }
            item {
                MemorySection("When") {
                    DetailRow("Arrival", formatMemoryTime(visit.startedAt))
                    DetailRow("Departure", visit.endedAt?.let(::formatMemoryTime) ?: "Not recorded")
                    DetailRow("Duration", formatDuration(visit.startedAt, visit.endedAt))
                    DetailRow("Timeline", "Memory #$timelinePosition")
                }
            }
            item {
                MemorySection("Place") {
                    DetailRow("Restaurant", visit.restaurant.name)
                    DetailRow("Address", visit.restaurant.address ?: "Not recorded")
                    DetailRow("Source", sourceLabel(visit.source.name))
                    if (visit.restaurant.categories.isNotEmpty()) {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            visit.restaurant.categories.forEach { AssistChip(onClick = {}, label = { Text(it.replace('_', ' ')) }) }
                        }
                    }
                    Button(onClick = { onRestaurantClick(visit.restaurant.id) }) { Text("Open restaurant memories") }
                }
            }
            item {
                MemorySection("Your history here") {
                    DetailRow("Previous visits", previousVisitCount.toString())
                    DetailRow("Later visits", futureVisitCount.toString())
                    DetailRow("Rating", visit.rating?.let { "$it / 5" } ?: "Unrated")
                }
            }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Box(Modifier.fillMaxWidth().padding(28.dp), contentAlignment = Alignment.Center) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Image, contentDescription = null)
                            Text("Photos will live here later", color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
            item {
                OutlinedButton(onClick = { editing = true }, modifier = Modifier.fillMaxWidth()) { Text("Edit this memory") }
            }
        }
    }
}

@Composable
private fun MemorySection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.secondary)
        Text(value)
    }
}
