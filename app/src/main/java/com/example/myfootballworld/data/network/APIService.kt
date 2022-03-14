package com.example.myfootballworld.data.network

import com.example.myfootballworld.data.model.AllTeamsResponseData
import com.example.myfootballworld.data.model.CompetitionsResponseData
import com.example.myfootballworld.data.model.Team
import com.example.myfootballworld.data.model.TeamDetailResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface APIService {

    @GET("competitions")
    suspend fun getAllCompetitions(
        @Header("X-Auth-Token") token : String,
        ): Response<CompetitionsResponseData>

    @GET("competitions/{id}/teams")
    suspend fun getAllTeams(
        @Header("X-Auth-Token") token : String,
        @Path("id") id: Int
        ): Response<AllTeamsResponseData>


    @GET("teams/{id}")
    suspend fun getTeam(
        @Header("X-Auth-Token") token : String,
        @Path("id") id: Int
        ): Response<TeamDetailResponseData>

}