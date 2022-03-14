package com.example.myfootballworld.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Area(
    val countryCode: String,
    val ensignUrl: String,
    val id: Int,
    val name: String
):Parcelable