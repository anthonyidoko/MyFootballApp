package com.example.myfootballworld.data.model

data class Squad(
    val countryOfBirth: String? = null,
    val dateOfBirth: String? = null,
    val id: Int,
    val name: String,
    val nationality: String? = null,
    val position: String? = null,
    val role: String? = null
)