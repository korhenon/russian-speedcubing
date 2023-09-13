package com.example.russianspeedcubing.model.application

data class Competitions(
    val current: List<Competition> = listOf(),
    val upcoming: List<Competition> = listOf(),
    val past: List<Competition> = listOf()
)
