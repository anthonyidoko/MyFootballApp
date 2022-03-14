package com.example.myfootballworld.di.module

import android.app.Application
import androidx.room.Room
import com.example.myfootballworld.data.database.db.AppDatabase
import com.example.myfootballworld.di.FootballApp
import com.example.myfootballworld.utils.Constants.Companion.DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase {
        val temInstance = INSTANCE
        if (temInstance != null) {
            return temInstance
        }

        synchronized(this) {
            val instance =
                Room.databaseBuilder(app.applicationContext, AppDatabase::class.java, DATABASE)
                    .build()
            INSTANCE = instance
            return instance
        }
    }

}