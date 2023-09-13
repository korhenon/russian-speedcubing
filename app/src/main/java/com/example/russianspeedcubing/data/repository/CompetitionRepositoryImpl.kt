package com.example.russianspeedcubing.data.repository

import com.example.russianspeedcubing.data.services.FuncubingService
import com.example.russianspeedcubing.model.application.Competition
import com.example.russianspeedcubing.model.application.Competitions
import java.time.LocalDate

class CompetitionRepositoryImpl : CompetitionRepository {
    private val service = FuncubingService.apiService

    override suspend fun getCompetitions(): Competitions {
        val allCompetitions = service.getCompetitions()
        val current = mutableListOf<Competition>()
        val upcoming = mutableListOf<Competition>()
        val past = mutableListOf<Competition>()

        val today = LocalDate.now()
        for (competition in allCompetitions) {
            if (competition.is_publish && competition.is_ranked) {
                val startDate = LocalDate.parse(competition.start_date)
                val endDate = LocalDate.parse(competition.end_date)
                if (today > endDate) {
                    past.add(competition.toCompetition())
                }
                else if (today < startDate) {
                    upcoming.add(competition.toCompetition())
                }
                else {
                    current.add(competition.toCompetition())
                }
            }
        }
        return Competitions(current, upcoming.reversed(), past)
    }
}