package com.rc.android.homework.data

import android.content.Context
import androidx.room.Room
import com.rc.android.homework.data.room.HabitDAO
import com.rc.android.homework.data.room.HabitRoomDatabase
import com.rc.android.homework.data.server.HabitTrackerNetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HabitRepositoryModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideHabitTrackerNetworkClient(): HabitTrackerNetworkClient = HabitTrackerNetworkClient()

    @Singleton
    @Provides
    fun provideHabitDAO(): HabitDAO{
        return Room.databaseBuilder( context.applicationContext, HabitRoomDatabase::class.java, "habit_database")
            .allowMainThreadQueries()
            .build()
            .getHabitDAO()
    }
}