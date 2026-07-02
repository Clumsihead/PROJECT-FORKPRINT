package com.forkprint.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.ui.util.formatDuration
import com.forkprint.app.ui.util.formatMemoryDate
import com.forkprint.app.ui.util.formatMemoryTime
import com.forkprint.app.ui.util.sourceLabel

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun VisitCard(
    visit: Visit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(formatMemoryDate(visit.startedAt), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
            Text(visit.restaurant.name, style = MaterialTheme.typography.titleLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(formatMemoryTime(visit.startedAt), style = MaterialTheme.typography.bodyMedium)
                Text("•", color = MaterialTheme.colorScheme.secondary)
                Text(formatDuration(visit.startedAt, visit.endedAt), style = MaterialTheme.typography.bodyMedium)
            }
            visit.note.takeIf { it.isNotBlank() }?.let {
                Text("“$it”", style = MaterialTheme.typography.bodyLarge, fontStyle = FontStyle.Italic)
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                visit.rating?.let { AssistChip(onClick = {}, label = { Text("$it / 5") }) }
                AssistChip(onClick = {}, label = { Text(sourceLabel(visit.source.name)) })
                visit.restaurant.categories.take(2).forEach { category ->
                    AssistChip(onClick = {}, label = { Text(category.replace('_', ' ')) })
                }
            }
        }
    }
}
