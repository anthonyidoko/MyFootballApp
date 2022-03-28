package com.example.myfootballworld.data.repository

import com.example.myfootballworld.data.model.AllTeamsResponseData
import com.example.myfootballworld.data.model.Competition
import com.example.myfootballworld.data.model.CompetitionsResponseData
import com.example.myfootballworld.data.model.TeamDetailResponseData
import retrofit2.Response

interface CompetitionsRepo {
    suspend fun getAllCompetitions() : Response<CompetitionsResponseData>

    suspend fun getAllTeams(id: Int) : Response<AllTeamsResponseData>

    suspend fun getTeams(id: Int) : Response<TeamDetailResponseData>

    suspend fun fetchCompetitionsFromDb(): List<Competition>

    suspend fun saveCompetitionsToDb(competitions: List<Competition>) : List<Long>


}