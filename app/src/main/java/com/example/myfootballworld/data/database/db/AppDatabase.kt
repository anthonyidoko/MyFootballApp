package com.example.myfootballworld.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myfootballworld.data.database.dao.CompetitionDao
import com.example.myfootballworld.data.model.Competition
import com.example.myfootballworld.utils.CompetitionTypeConverter

@Database(
    entities = [Competition::class], version = 1, exportSchema = false,
)
@TypeConverters(CompetitionTypeConverter::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun competitionsDao() : CompetitionDao
}