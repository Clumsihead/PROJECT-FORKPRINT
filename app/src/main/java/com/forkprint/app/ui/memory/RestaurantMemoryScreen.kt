package com.forkprint.app.ui.memory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.forkprint.app.domain.model.Restaurant
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.domain.usecase.BuildRestaurantMemory
import com.forkprint.app.ui.components.VisitCard
import com.forkprint.app.ui.util.formatMemoryDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RestaurantMemoryScreen(
    restaurant: Restaurant,
    allVisits: List<Visit>,
    onBack: () -> Unit,
    onVisitClick: (String) -> Unit,
) {
    val memory = BuildRestaurantMemory.from(restaurant, allVisits)
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Place memories") },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") } },
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Your history with", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
                    Text(restaurant.name, style = MaterialTheme.typography.displaySmall)
                    restaurant.address?.let { Text(it, style = MaterialTheme.typography.bodyLarge) }
                    if (restaurant.categories.isNotEmpty()) {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            restaurant.categories.forEach { AssistChip(onClick = {}, label = { Text(it.replace('_', ' ')) }) }
                        }
                    }
                }
            }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatRow("Visit count", memory.visitCount.toString())
                        StatRow("First visit", memory.firstVisitAt?.let(::formatMemoryDate) ?: "Not yet")
                        StatRow("Most recent", memory.mostRecentVisitAt?.let(::formatMemoryDate) ?: "Not yet")
                        StatRow("Average rating", memory.averageRating?.let { String.format("%.1f / 5", it) } ?: "Unrated")
                    }
                }
            }
            if (memory.notes.isNotEmpty()) {
                item {
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("Notes you left here", style = MaterialTheme.typography.titleMedium)
                            memory.notes.forEach { note ->
                                Text("“$note”", style = MaterialTheme.typography.bodyLarge, fontStyle = FontStyle.Italic)
                            }
                        }
                    }
                }
            }
            item { Text("Complete timeline", style = MaterialTheme.typography.titleLarge) }
            items(memory.visits, key = { it.id }) { visit -> VisitCard(visit, onClick = { onVisitClick(visit.id) }) }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.secondary)
        Text(value)
    }
}
