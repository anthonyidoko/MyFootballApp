package com.example.myfootballworld.di.module

import com.example.myfootballworld.data.database.db.AppDatabase
import com.example.myfootballworld.data.network.APIService
import com.example.myfootballworld.data.repository.CompetitionsRepo
import com.example.myfootballworld.data.repository.CompetitionsRepoIpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(service: APIService,db:AppDatabase): CompetitionsRepo{
        return CompetitionsRepoIpl(service,db)
    }
}