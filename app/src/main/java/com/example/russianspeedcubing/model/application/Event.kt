package com.example.russianspeedcubing.model.application

import com.example.russianspeedcubing.model.network.PsychSheetItem
import com.example.russianspeedcubing.model.network.Round

data class Event(
    val id: String,
    val icon: Int,
    val iconSelected: Int,
    val rounds: List<Round>,
    var psychSheet: List<PsychSheetItem> = listOf()
)
