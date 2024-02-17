package com.example.russianspeedcubing.model.network

data class FuncubingResult(
    val attempts: List<Any>,
    val average: Int,
    val best: Any,
    val competition_id: String,
    val event_id: String,
    val fc_id: String,
    val id: Int,
    val name: String,
    val pos: Int,
    val registration_id: String,
    val round: Int,
    val wca_id: String
)