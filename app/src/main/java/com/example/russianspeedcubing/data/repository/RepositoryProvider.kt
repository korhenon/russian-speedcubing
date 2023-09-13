package com.example.russianspeedcubing.data.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider {
    @Provides
    fun provideCompetitionRepository(): CompetitionRepository {
        return CompetitionRepositoryImpl()
    }
}