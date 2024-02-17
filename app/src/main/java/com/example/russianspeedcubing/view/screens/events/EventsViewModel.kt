package com.example.russianspeedcubing.view.screens.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russianspeedcubing.data.repository.CompetitionRepository
import com.example.russianspeedcubing.data.repository.CompetitorRepository
import com.example.russianspeedcubing.model.application.Event
import com.example.russianspeedcubing.model.network.FuncubingResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: CompetitionRepository,
    competitorRepository: CompetitorRepository
) : ViewModel() {
    var isLoadEvents by mutableStateOf(true)
    var isLoadResults by mutableStateOf(false)
    var isLoadPsychsheet by mutableStateOf(false)
    var isFirstResultLoaded by mutableStateOf(false)
    var events by mutableStateOf(listOf<Event>())
    var selectedEventId by mutableStateOf(0)
    var selectedRoundId by mutableStateOf(0)
    var selectedRoundResults by mutableStateOf(listOf<FuncubingResult>())
    val notSelectedEvents get() = events.filter { it.id != events[selectedEventId].id }
    val selectedEvent get() = events[selectedEventId]
    val selectedRound get() = selectedEvent.rounds[selectedRoundId]
    val savedCompetitorId = competitorRepository.getSavedCompetitorId()
    private var needLoadEvents by mutableStateOf(true)
    private var competitionId by mutableStateOf("")

    fun setup(id: String) {
        needLoadEvents = competitionId != id
        competitionId = id
        isLoadEvents = true
        isLoadResults = false
        selectedEventId = 0
        selectedRoundId = 0
        isFirstResultLoaded = false
    }

    fun loadEvents() {
        if (needLoadEvents) {
            viewModelScope.launch {
                selectedEventId = 0
                events = repository.getEvents(competitionId)
                isLoadEvents = false
                needLoadEvents = false
            }
        } else {
            isLoadEvents = false
        }
    }

    fun getPsychSheetForSelectedEvent() {
        if (events[selectedEventId].psychSheet.isEmpty()) {
            viewModelScope.launch {
                isLoadPsychsheet = true
                val newList = events.toMutableList()
                newList[selectedEventId].psychSheet =
                    repository.getPsychsheet(competitionId, selectedEvent.id)
                events = newList
                isLoadPsychsheet = false
            }
        }
    }

    fun loadResults() {
        viewModelScope.launch {
            isLoadResults = true
            if (selectedRoundId >= 0) selectedRoundResults =
                repository.getResults(competitionId, selectedRound.id)
            isLoadResults = false
            isFirstResultLoaded = true
        }
    }
}