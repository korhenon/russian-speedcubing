package com.example.russianspeedcubing.data.services

import com.example.russianspeedcubing.model.network.FuncubingCompetition
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

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

}