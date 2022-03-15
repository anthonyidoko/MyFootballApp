package com.example.myfootballworld.data.database.db

import android.content.Context
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myfootballworld.data.database.dao.CompetitionDao
import com.example.myfootballworld.data.model.Area
import com.example.myfootballworld.data.model.Competition
import com.example.myfootballworld.data.model.CurrentSeason
import com.example.myfootballworld.data.model.Winner
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest: TestCase(){

    private lateinit var db: AppDatabase
    private lateinit var dao: CompetitionDao

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.competitionsDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun insertAndRetrieveCompetitions() = runBlocking{
        val area = Area("1000", "url", 100, "Lagos")
        val area2 = Area("1002", "url", 102, "Lagos")
        val area1 = Area("1001", "url", 101, "Lagos")
        val winner = Winner("crest url", 100, "Madrid", "Madrid", "tla")
        val winner1 = Winner("crest url", 101, "Barca", "Barca", "tla")
        val winner2 = Winner("crest url", 102, "PSG", "PSG", "tla")
        val currentSeason = CurrentSeason(1, "today", 100, "yesterday", winner)
        val currentSeason1 = CurrentSeason(1, "today", 101, "yesterday", winner1)
        val currentSeason2 = CurrentSeason(1, "today", 102, "yesterday", winner2)
        val competition = Competition(area, "120", currentSeason, "emblem", 1, "today", "Madrid", 1, "none")
        val competition1 = Competition(area1, "120", currentSeason1, "emblem", 2, "today", "Barca", 1, "none")
        val competition2 = Competition(area2, "120", currentSeason2, "emblem", 3, "today", "Barca", 1, "none")
        val competitionList = listOf(competition, competition1, competition2)

        dao.saveAllCompetitionsToDb(competitionList)

        val competitions = dao.fetchAllCompetitionsFromRoom()
        assertThat(competitions.containsAll(competitionList)).isTrue()
    }
}