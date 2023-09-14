package com.example.russianspeedcubing.model.network

data class PsychSheetItem(
    val average: Int,
    val fc: Fc,
    val fc_id: String,
    val name: String,
    val single: Int,
    val wca: Wca,
    val wca_id: String
)