package com.example.russianspeedcubing.view.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russianspeedcubing.data.repository.CompetitionRepository
import com.example.russianspeedcubing.model.application.Competitions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CompetitionRepository
): ViewModel() {
    var competitions by mutableStateOf(Competitions())
    var isLoad by mutableStateOf(false)
    fun loadCompetitions() {
        viewModelScope.launch {
            isLoad = true
            competitions = repository.getCompetitions()
            isLoad = false
        }
    }

}