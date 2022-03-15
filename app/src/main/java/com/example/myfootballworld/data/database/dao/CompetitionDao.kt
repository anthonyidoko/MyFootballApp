package com.example.myfootballworld.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfootballworld.data.model.Competition

@Dao
interface CompetitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCompetitionsToDb(competitions: List<Competition>): List<Long>

    @Query("SELECT * FROM competition_table")
    suspend fun fetchAllCompetitionsFromRoom(): List<Competition>
}