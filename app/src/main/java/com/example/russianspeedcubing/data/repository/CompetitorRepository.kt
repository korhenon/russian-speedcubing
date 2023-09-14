package com.example.russianspeedcubing.data.repository

import com.example.russianspeedcubing.model.network.Competitor

interface CompetitorRepository {
    suspend fun getFCCompetitors(): List<Competitor>
    fun hasSavedCompetitor(): Boolean
    fun saveCompetitor(fcId: String, name: String)
    fun getSavedCompetitorId(): String
    fun getSavedCompetitorName(): String

}