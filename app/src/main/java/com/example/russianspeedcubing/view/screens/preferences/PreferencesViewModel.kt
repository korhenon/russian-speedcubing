package com.example.russianspeedcubing.view.screens.preferences

import androidx.lifecycle.ViewModel
import com.example.russianspeedcubing.data.repository.CompetitorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val repository: CompetitorRepository
) : ViewModel() {
    val fcId get() = repository.getSavedCompetitorId()
    val name get() = repository.getSavedCompetitorName()
    val hasCompetitor get() = repository.hasSavedCompetitor()
}