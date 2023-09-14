package com.example.russianspeedcubing.data.repository

import com.example.russianspeedcubing.model.application.Competitions
import com.example.russianspeedcubing.model.application.Event
import com.example.russianspeedcubing.model.network.PsychSheetItem

interface CompetitionRepository {
    suspend fun getCompetitions(): Competitions
    fun getDescription(id: String): String
    suspend fun getEvents(id: String): List<Event>
    suspend fun getPsychsheet(id: String, event: String): List<PsychSheetItem>
}