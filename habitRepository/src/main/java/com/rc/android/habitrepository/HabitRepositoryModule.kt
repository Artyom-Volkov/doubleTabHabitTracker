package com.rc.android.habitrepository

import android.content.Context
import androidx.room.Room
import com.rc.android.habitrepository.room.HabitDAO
import com.rc.android.habitrepository.room.HabitRoomDatabase
import com.rc.android.habitrepository.server.HabitTrackerNetworkClient
import com.rc.android.habittracker.HabitRepositoryI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HabitRepositoryModule() {

    @Provides
    fun provideHabitRepository(networkClient: HabitTrackerNetworkClient, habitDAO: HabitDAO): HabitRepositoryI
            = HabitRepository(networkClient , habitDAO)

    @Singleton
    @Provides
    fun provideHabitTrackerNetworkClient(): HabitTrackerNetworkClient = HabitTrackerNetworkClient()

    @Singleton
    @Provides
    fun provideHabitDAO(context: Context): HabitDAO {
        return Room.databaseBuilder( context.applicationContext, HabitRoomDatabase::class.java, "habit_database")
            .allowMainThreadQueries()
            .build()
            .getHabitDAO()
    }
}