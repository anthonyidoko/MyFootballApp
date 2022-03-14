package com.example.myfootballworld.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class CurrentSeason(
    var currentMatchday: Int? = null,
    var endDate: String? = null,
    var id: Int? = null,
    var startDate: String? = null,
    var winner: Winner? = null
)