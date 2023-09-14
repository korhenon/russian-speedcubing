package com.example.russianspeedcubing.data.repository

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider {
    @Provides
    fun provideCompetitionRepository(): CompetitionRepository {
        return CompetitionRepositoryImpl()
    }

    @Provides
    fun provideCompetitorRepository(@ApplicationContext context: Context): CompetitorRepository {
        return CompetitorRepositoryImpl(context)
    }
}