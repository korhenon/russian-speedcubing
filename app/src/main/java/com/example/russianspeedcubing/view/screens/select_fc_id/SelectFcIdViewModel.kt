package com.example.russianspeedcubing.view.screens.select_fc_id

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russianspeedcubing.data.repository.CompetitorRepository
import com.example.russianspeedcubing.model.network.Competitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFcIdViewModel @Inject constructor(
    private val repository: CompetitorRepository
) : ViewModel() {
    var isFirstLoad by mutableStateOf(true)
    var isLoad by mutableStateOf(false)
    private var competitors by mutableStateOf(listOf<Competitor>())
    var search by mutableStateOf("")

    val filteredCompetitors
        get() = competitors.filter { competitor ->
            if (" " !in search) competitor.name.lowercase().split(" ")
                .any { search.lowercase() in it }
            else search.lowercase() in competitor.name.lowercase()
        }

    fun load() {
        viewModelScope.launch {
            isLoad = true
            competitors = repository.getFCCompetitors()
            isLoad = false
            isFirstLoad = false
        }
    }

    fun save(competitor: Competitor) {
        repository.saveCompetitor(competitor.fc_id!!, competitor.name)
    }
}