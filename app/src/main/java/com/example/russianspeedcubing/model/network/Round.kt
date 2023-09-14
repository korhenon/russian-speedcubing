package com.example.russianspeedcubing.model.network

data class Round(
    val advance_to_next_round: AdvanceToNextRound?,
    val attempts: Int,
    val competition_id: String,
    val cutoff: String?,
    val cutoff_attempts: Int,
    val event: String,
    val format: String,
    val format_type: String,
    val id: String,
    val is_special: Boolean,
    val name: String,
    val result: String,
    val result_type: String,
    val round: RoundX,
    val time_limit: String?,
    val time_limit_cumulative: String?
) {
    fun short_format(): String {
        return format.split(" ").map { it[0] }.joinToString("")
    }
}