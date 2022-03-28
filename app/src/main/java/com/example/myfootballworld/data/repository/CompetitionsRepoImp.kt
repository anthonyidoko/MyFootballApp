package com.example.myfootballworld.data.repository

import com.example.myfootballworld.data.database.db.AppDatabase
import com.example.myfootballworld.data.model.*
import com.example.myfootballworld.data.network.APIService
import retrofit2.Response
import javax.inject.Inject

class CompetitionsRepoImp @Inject constructor(
    private val service: APIService,
    private val db: AppDatabase
) : CompetitionsRepo {

    companion object {
        init {
            System.loadLibrary("myfootballworld")
        }
    }

    external fun getApiKey(): String


    override suspend fun getAllCompetitions(): Response<CompetitionsResponseData> {
        return service.getAllCompetitions(getApiKey())
    }

    override suspend fun getAllTeams(id: Int): Response<AllTeamsResponseData> {
        return service.getAllTeams(getApiKey(), id)
    }

    override suspend fun getTeams(id: Int): Response<TeamDetailResponseData> {
        return service.getTeam(getApiKey(), id)
    }

    override suspend fun fetchCompetitionsFromDb(): List<Competition> {
        return db.competitionsDao().fetchAllCompetitionsFromRoom()
    }

    override suspend fun saveCompetitionsToDb(competitions: List<Competition>): List<Long> {
        return db.competitionsDao().saveAllCompetitionsToDb(competitions)
    }


}