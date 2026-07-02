package com.forkprint.app.ai

import com.forkprint.app.domain.model.Visit

interface AiMemoryProvider {
    val isAvailable: Boolean
    suspend fun summarizeVisits(visits: List<Visit>): String?
    suspend fun answerQuestion(question: String, visits: List<Visit>): String?
}

class NoOpAiMemoryProvider : AiMemoryProvider {
    override val isAvailable: Boolean = false
    override suspend fun summarizeVisits(visits: List<Visit>): String? = null
    override suspend fun answerQuestion(question: String, visits: List<Visit>): String? = null
}
