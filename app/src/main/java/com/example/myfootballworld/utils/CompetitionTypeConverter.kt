package com.example.myfootballworld.utils

import androidx.room.TypeConverter
import com.example.myfootballworld.data.model.Area
import com.example.myfootballworld.data.model.CurrentSeason
import com.example.myfootballworld.data.model.Winner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CompetitionTypeConverter {

    @TypeConverter
    fun fromStringToArea(value: String) : Area{
        val area = object : TypeToken<Area>(){}.type
        return Gson().fromJson(value, area)
    }

    @TypeConverter
    fun toStringFromArea(value: Area): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToCurrentSeason(value: String?) : CurrentSeason?{
        if (value == null){
            return null
        }
        val currentSeason = object : TypeToken<CurrentSeason>(){}.type
        return Gson().fromJson(value, currentSeason)
    }

    @TypeConverter
    fun toStringFromCurrentSeason(value: CurrentSeason?): String?{
        if (value == null){
            return null
        }
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringFromWinner(value: Winner): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToWinner(value: String) : Winner{
        val winner = object : TypeToken<Winner>(){}.type
        return Gson().fromJson(value, winner)
    }
}