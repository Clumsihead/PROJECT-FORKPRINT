package com.forkprint.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.forkprint.app.domain.model.Restaurant
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.ui.analytics.AnalyticsScreen
import com.forkprint.app.ui.memory.RestaurantMemoryScreen
import com.forkprint.app.ui.memory.VisitDetailScreen
import com.forkprint.app.ui.search.SearchScreen
import com.forkprint.app.ui.timeline.TimelineScreen

enum class ForkprintTab(val label: String) { Timeline("Timeline"), Search("Search"), Analytics("Insights") }

private sealed interface ForkprintScreen {
    data object Tabs : ForkprintScreen
    data class VisitDetail(val visitId: String) : ForkprintScreen
    data class RestaurantMemory(val restaurantId: String) : ForkprintScreen
}

@Composable
fun ForkprintApp(viewModel: ForkprintViewModel = hiltViewModel()) {
    var selectedTab by remember { mutableStateOf(ForkprintTab.Timeline) }
    var screen by remember { mutableStateOf<ForkprintScreen>(ForkprintScreen.Tabs) }
    val visits by viewModel.timeline.collectAsState()

    when (val current = screen) {
        ForkprintScreen.Tabs -> {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        ForkprintTab.entries.forEach { tab ->
                            NavigationBarItem(
                                selected = selectedTab == tab,
                                onClick = { selectedTab = tab },
                                icon = {
                                    Icon(
                                        imageVector = when (tab) {
                                            ForkprintTab.Timeline -> Icons.Default.Timeline
                                            ForkprintTab.Search -> Icons.Default.Search
                                            ForkprintTab.Analytics -> Icons.Default.Analytics
                                        },
                                        contentDescription = tab.label,
                                    )
                                },
                                label = { Text(tab.label) },
                            )
                        }
                    }
                },
            ) { padding ->
                when (selectedTab) {
                    ForkprintTab.Timeline -> TimelineScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                        onVisitClick = { screen = ForkprintScreen.VisitDetail(it) },
                    )
                    ForkprintTab.Search -> SearchScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                        onVisitClick = { screen = ForkprintScreen.VisitDetail(it) },
                    )
                    ForkprintTab.Analytics -> AnalyticsScreen(modifier = Modifier.padding(padding), viewModel = viewModel)
                }
            }
        }
        is ForkprintScreen.VisitDetail -> {
            val visit = visits.firstOrNull { it.id == current.visitId }
            if (visit == null) {
                screen = ForkprintScreen.Tabs
            } else {
                VisitDetailScreen(
                    visit = visit,
                    timelinePosition = visits.indexOfFirst { it.id == visit.id }.takeIf { it >= 0 }?.plus(1) ?: 1,
                    previousVisitCount = visits.count { it.restaurant.id == visit.restaurant.id && it.startedAt.isBefore(visit.startedAt) },
                    futureVisitCount = visits.count { it.restaurant.id == visit.restaurant.id && it.startedAt.isAfter(visit.startedAt) },
                    viewModel = viewModel,
                    onBack = { screen = ForkprintScreen.Tabs },
                    onRestaurantClick = { screen = ForkprintScreen.RestaurantMemory(it) },
                )
            }
        }
        is ForkprintScreen.RestaurantMemory -> {
            val restaurant = visits.firstOrNull { it.restaurant.id == current.restaurantId }?.restaurant
            if (restaurant == null) {
                screen = ForkprintScreen.Tabs
            } else {
                RestaurantMemoryScreen(
                    restaurant = restaurant,
                    allVisits = visits,
                    onBack = { screen = ForkprintScreen.Tabs },
                    onVisitClick = { screen = ForkprintScreen.VisitDetail(it) },
                )
            }
        }
    }
}
