package com.example.russianspeedcubing.data.repository

import com.example.russianspeedcubing.data.constants.EventsIconsMap
import com.example.russianspeedcubing.data.constants.EventsSelectedIconsMap
import com.example.russianspeedcubing.data.services.FuncubingService
import com.example.russianspeedcubing.model.application.Competition
import com.example.russianspeedcubing.model.application.Competitions
import com.example.russianspeedcubing.model.application.Event
import com.example.russianspeedcubing.model.network.PsychSheetItem
import com.example.russianspeedcubing.model.network.FuncubingResult
import com.example.russianspeedcubing.model.network.Round
import org.jsoup.Jsoup
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
                } else if (today < startDate) {
                    upcoming.add(competition.toCompetition())
                } else {
                    current.add(competition.toCompetition())
                }
            }
        }
        return Competitions(current, upcoming.reversed(), past)
    }

    override fun getDescription(id: String): String {
        return Jsoup.connect("https://funcubing.org/competitions/$id")
            .get().select("td").last()!!.html()
    }

    override suspend fun getEvents(id: String): List<Event> {
        val allRounds = service.getEvents(id)
        val events = mutableListOf<Event>()
        var currentEvent = allRounds[0].event
        var rounds = mutableListOf<Round>()

        for (round in allRounds) {
            if (currentEvent == round.event) {
                rounds.add(round)
            } else {
                if (EventsIconsMap.containsKey(currentEvent)) {
                    events.add(
                        Event(
                            currentEvent,
                            EventsIconsMap[currentEvent]!!,
                            EventsSelectedIconsMap[currentEvent]!!,
                            rounds
                        )
                    )
                }
                rounds = mutableListOf()
                rounds.add(round)
                currentEvent = round.event
            }
        }
        if (EventsIconsMap.containsKey(currentEvent)) {
            events.add(
                Event(
                    currentEvent,
                    EventsIconsMap[currentEvent]!!,
                    EventsSelectedIconsMap[currentEvent]!!,
                    rounds
                )
            )
        }
        return events
    }

    override suspend fun getPsychsheet(id: String, event: String): List<PsychSheetItem> {
        return service.getPsychsheet(id, event)
    }

    override suspend fun getResults(id: String, round: String): List<FuncubingResult> {
        return service.getResults(id).filter { it.event_id == round }
    }

}