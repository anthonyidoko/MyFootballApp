package com.example.myfootballworld.data.model

data class Season(
    val availableStages: List<String>,
    val currentMatchday: Any,
    val endDate: String,
    val id: Int,
    val startDate: String
)