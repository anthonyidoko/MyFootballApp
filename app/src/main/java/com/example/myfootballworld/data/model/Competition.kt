package com.example.myfootballworld.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity(tableName = "competition_table")
data class Competition(
    var area: Area,
    var code: String? = null,
    var currentSeason: CurrentSeason? = null,
    var emblemUrl: String? = null,
    @PrimaryKey
    val id: Int,
    var lastUpdated: String,
    val name: String,
    var numberOfAvailableSeasons: Int,
    var plan: String? = null
)