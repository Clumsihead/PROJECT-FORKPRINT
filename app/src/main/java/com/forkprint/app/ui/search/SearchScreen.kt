package com.forkprint.app.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.forkprint.app.ui.ForkprintViewModel
import com.forkprint.app.ui.components.VisitCard

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: ForkprintViewModel,
    onVisitClick: (String) -> Unit = {},
) {
    val query by viewModel.query.collectAsState()
    val results by viewModel.searchResults.collectAsState()
    Column(
        modifier.then(Modifier.fillMaxSize().padding(20.dp)),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Rediscover", style = MaterialTheme.typography.headlineMedium)
        Text("Search your notes, places, and remembered meals.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.query.value = it },
            label = { Text("Search memories") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        if (results.isEmpty()) {
            Card(Modifier.fillMaxWidth().padding(top = 16.dp)) {
                Text(
                    if (query.isBlank()) "Your memories will appear here as you type." else "No matching memories yet.",
                    modifier = Modifier.padding(18.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(results, key = { it.id }) { VisitCard(it, onClick = { onVisitClick(it.id) }) }
            }
        }
    }
}
