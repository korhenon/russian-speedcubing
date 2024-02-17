package com.example.russianspeedcubing.data.repository

import com.example.russianspeedcubing.model.application.Competitions
import com.example.russianspeedcubing.model.application.Event
import com.example.russianspeedcubing.model.network.PsychSheetItem
import com.example.russianspeedcubing.model.network.FuncubingResult


interface CompetitionRepository {
    suspend fun getCompetitions(): Competitions
    fun getDescription(id: String): String
    suspend fun getEvents(id: String): List<Event>
    suspend fun getPsychsheet(id: String, event: String): List<PsychSheetItem>
    suspend fun getResults(id: String, round: String): List<FuncubingResult>
}