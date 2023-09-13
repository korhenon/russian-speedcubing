package com.example.russianspeedcubing.data.repository

import com.example.russianspeedcubing.model.application.Competitions

interface CompetitionRepository {
    suspend fun getCompetitions(): Competitions
    fun getDescription(id: String): String
}