package com.forkprint.app.ui.memory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.domain.model.VisitUpdate
import com.forkprint.app.ui.util.formatEditDateTime
import com.forkprint.app.ui.util.parseEditDateTime

@Composable
fun EditVisitDialog(
    visit: Visit,
    onDismiss: () -> Unit,
    onSave: (VisitUpdate) -> Unit,
) {
    var restaurantName by remember(visit.id) { mutableStateOf(visit.restaurant.name) }
    var address by remember(visit.id) { mutableStateOf(visit.restaurant.address.orEmpty()) }
    var categories by remember(visit.id) { mutableStateOf(visit.restaurant.categories.joinToString(", ")) }
    var startedAt by remember(visit.id) { mutableStateOf(formatEditDateTime(visit.startedAt)) }
    var endedAt by remember(visit.id) { mutableStateOf(visit.endedAt?.let(::formatEditDateTime).orEmpty()) }
    var note by remember(visit.id) { mutableStateOf(visit.note) }
    var rating by remember(visit.id) { mutableStateOf(visit.rating) }
    val parsedStarted = parseEditDateTime(startedAt)
    val parsedEnded = endedAt.takeIf { it.isNotBlank() }?.let(::parseEditDateTime)
    val canSave = restaurantName.isNotBlank() && parsedStarted != null && (endedAt.isBlank() || parsedEnded != null)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit memory") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(restaurantName, { restaurantName = it }, label = { Text("Restaurant") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(address, { address = it }, label = { Text("Address or area") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(categories, { categories = it }, label = { Text("Categories") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(startedAt, { startedAt = it }, label = { Text("Arrival (yyyy-MM-dd HH:mm)") }, singleLine = true, isError = parsedStarted == null, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(endedAt, { endedAt = it }, label = { Text("Departure (optional)") }, singleLine = true, isError = endedAt.isNotBlank() && parsedEnded == null, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(
                    note,
                    { note = it },
                    label = { Text("Private note") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (1..5).forEach { value ->
                        FilterChip(
                            selected = rating == value,
                            onClick = { rating = if (rating == value) null else value },
                            label = { Text(value.toString()) },
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = canSave,
                onClick = {
                    onSave(
                        VisitUpdate(
                            restaurantName = restaurantName,
                            address = address.takeIf { it.isNotBlank() },
                            categories = categories.split(',').map { it.trim() }.filter { it.isNotBlank() },
                            startedAt = parsedStarted!!,
                            endedAt = parsedEnded,
                            rating = rating,
                            note = note,
                        ),
                    )
                },
            ) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}
