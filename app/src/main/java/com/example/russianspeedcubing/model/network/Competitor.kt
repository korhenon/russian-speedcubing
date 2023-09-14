package com.example.russianspeedcubing.model.network

data class Competitor(
    val name: String,
    val fc_id: String?,
    val wca_id: String?
) {
    fun withRussianName(): Competitor {
        return Competitor(
            name.replace("(", "").replace(")", "")
                .split(" ").drop(2).take(2).joinToString(" "),
            fc_id,
            wca_id
        )
    }
}