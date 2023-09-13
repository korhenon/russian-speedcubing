package com.example.russianspeedcubing.model.application

data class Competition(
    val id: String,
    val name: String,
    val dates: String,
    val month: String,
    val year: String,
    val city: String,
    val site: String,
    val organizers: String,
    val delegates: String,
    val competitorsCount: String,
    val logo: String,
    val dayNumber: Int,
)
