package com.example.myfootballworld.data.model


data class CompetitionsResponseData(
    val competitions: List<Competition>,
    val count: Int,
    val filters: Filters
)