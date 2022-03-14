package com.example.myfootballworld.data.model

data class AllTeamsResponseData(
    val competition: CompetitionX,
    val count: Int,
    val filters: FiltersX,
    val season: Season,
    val teams: List<Team>
)