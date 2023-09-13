package com.example.russianspeedcubing.model.network

import com.example.russianspeedcubing.data.constants.GetMonth
import com.example.russianspeedcubing.model.application.Competition
import java.time.LocalDate
import java.time.Period

data class FuncubingCompetition(
    val city: String,
    val competitor_limit: Int,
    val competitors_count: String,
    val delegates: List<Delegate>,
    val details: String,
    val end_date: String,
    val id: String,
    val is_approved: Boolean,
    val is_live_cubingrf: Any,
    val is_publish: Boolean,
    val is_ranked: Boolean,
    val local_id: String,
    val logo: String,
    val my_roles: Any,
    val name: String,
    val organizers: List<Organizer>,
    val points: Any,
    val sheets: List<Sheet>,
    val start_date: String,
    val url: String,
    val website: String,
    val wrong_attempts: String
) {
    fun toCompetition(): Competition {
        val startDateSplit = start_date.split("-")
        val endDateSplit = end_date.split("-")
        return Competition(
            id,
            name,
            if (start_date == end_date) startDateSplit[2]
            else startDateSplit[2] + "-" + endDateSplit[2],
            GetMonth(startDateSplit[1].toInt()),
            startDateSplit[0],
            city,
            website,
            organizers.joinToString(",") { it.name },
            delegates.joinToString(",") { it.name },
            if (competitor_limit == 0) competitors_count
            else "$competitors_count / $competitor_limit",
            logo,
            Period.between(LocalDate.parse(start_date), LocalDate.now()).days + 1
        )
    }
}