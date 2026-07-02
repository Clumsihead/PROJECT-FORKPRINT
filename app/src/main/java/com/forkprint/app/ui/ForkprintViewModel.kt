package com.forkprint.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forkprint.app.domain.model.LocalAnalytics
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.domain.model.VisitDraft
import com.forkprint.app.domain.model.VisitUpdate
import com.forkprint.app.domain.repository.VisitRepository
import com.forkprint.app.location.RestaurantVisitDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ForkprintViewModel @Inject constructor(
    private val repository: VisitRepository,
    private val visitDetector: RestaurantVisitDetector,
) : ViewModel() {
    val timeline: StateFlow<List<Visit>> = repository.observeTimeline().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val analytics: StateFlow<LocalAnalytics> = repository.observeAnalytics().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalAnalytics(0, 0, null, null))
    val query = MutableStateFlow("")
    val searchResults: StateFlow<List<Visit>> = query.flatMapLatest(repository::searchVisits).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private var detectionJob: Job? = null

    fun startVisitDetection() {
        if (detectionJob?.isActive == true) return
        detectionJob = viewModelScope.launch {
            visitDetector.detectedVisits().collect { draft -> repository.addVisit(draft) }
        }
    }

    fun addManualVisit(draft: VisitDraft) = viewModelScope.launch { repository.addVisit(draft) }
    fun updateVisit(visitId: String, update: VisitUpdate) = viewModelScope.launch { repository.updateVisit(visitId, update) }
    fun deleteVisit(visitId: String) = viewModelScope.launch { repository.deleteVisit(visitId) }
    fun rateVisit(visitId: String, rating: Int?) = viewModelScope.launch { repository.updateVisitRating(visitId, rating) }
    fun updateNote(visitId: String, note: String) = viewModelScope.launch { repository.updateVisitNote(visitId, note) }
}
