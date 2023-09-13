package com.example.russianspeedcubing.view.screens.competition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russianspeedcubing.data.repository.CompetitionRepository
import com.example.russianspeedcubing.model.application.Competition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(
    private val repository: CompetitionRepository
) : ViewModel() {
    var competition by mutableStateOf(Competition())
    var isLoad by mutableStateOf(true)
    var descriptionHtml by mutableStateOf("")

    fun loadDescription() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                descriptionHtml = repository.getDescription(competition.id)
                isLoad = false
            }
        }
    }
}