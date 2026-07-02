package com.forkprint.app.domain.usecase

import com.forkprint.app.domain.repository.VisitRepository
import javax.inject.Inject

class SearchVisits @Inject constructor(private val repository: VisitRepository) {
    operator fun invoke(query: String) = repository.searchVisits(query.trim())
}
