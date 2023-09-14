package com.example.russianspeedcubing.data.repository

import android.content.Context
import com.example.russianspeedcubing.data.services.FuncubingService
import com.example.russianspeedcubing.model.network.Competitor

class CompetitorRepositoryImpl constructor(
    context: Context
) : CompetitorRepository {
    private val sharedPreferencesName = "RS_SP"
    private val spCompetitorId = "competitorId"
    private val spCompetitorName = "competitorName"
    private val sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
    private val service = FuncubingService.apiService

    override suspend fun getFCCompetitors(): List<Competitor> {
        return service.getCompetitors().filter { it.fc_id != null }.map { it.withRussianName() }
            .reversed()
    }

    override fun hasSavedCompetitor(): Boolean {
        return sp.getString(spCompetitorId, "?") != "?"
    }

    override fun saveCompetitor(fcId: String, name: String) {
        val editor = sp.edit()
        editor.putString(spCompetitorName, name)
        editor.putString(spCompetitorId, fcId)
        editor.apply()
    }

    override fun getSavedCompetitorId(): String {
        return sp.getString(spCompetitorId, "?")!!
    }

    override fun getSavedCompetitorName(): String {
        return sp.getString(spCompetitorName, "?")!!
    }
}