package com.example.russianspeedcubing.data.services

import com.example.russianspeedcubing.model.network.FuncubingCompetition
import com.example.russianspeedcubing.model.network.PsychSheetItem
import com.example.russianspeedcubing.model.network.Round
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FuncubingService {
    companion object {
        private const val BASE_URL = "https://funcubing.org/api/"
        val apiService: FuncubingService by lazy {
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build().create(FuncubingService::class.java)
        }
    }

    @GET("competitions")
    suspend fun getCompetitions(): List<FuncubingCompetition>

    @GET("competitions/{id}/events")
    suspend fun getEvents(@Path("id") id: String): List<Round>

    @GET("competitions/{id}/psychsheet/{event}")
    suspend fun getPsychsheet(
        @Path("id") id: String,
        @Path("event") event: String
    ): List<PsychSheetItem>
}