package com.example.myfootballworld.data.repository

import com.example.myfootballworld.data.database.db.AppDatabase
import com.example.myfootballworld.data.model.*
import com.example.myfootballworld.data.network.APIService
import retrofit2.Response
import javax.inject.Inject

class CompetitionsRepoIpl @Inject constructor(private val service: APIService,private val db:AppDatabase):CompetitionsRepo {

    override suspend fun getAllCompetitions(key: String): Response<CompetitionsResponseData> {
        return service.getAllCompetitions(key)
    }

    override suspend fun getAllTeams(key: String, id: Int): Response<AllTeamsResponseData> {
        return service.getAllTeams(key, id)
    }

    override suspend fun getTeams(key: String, id: Int): Response<TeamDetailResponseData> {
        return service.getTeam(key, id)
    }

    override suspend fun fetchCompetitionsFromDb(): List<Competition> {
        return db.competitionsDao().fetchAllCompetitionsFromRoom()
    }

    override suspend fun saveCompetitionsToDb(competitions: List<Competition>): List<Long> {
        return db.competitionsDao().saveAllCompetitionsToDb(competitions)
    }


}