package com.rc.android.homework.ui

import android.content.Context
import com.rc.android.habittracker.HabitRepositoryI
import com.rc.android.habittracker.HabitTracker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HabitTrackerUIModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideHabitTracker(habitRepository: HabitRepositoryI): HabitTracker = HabitTracker(habitRepository)
}