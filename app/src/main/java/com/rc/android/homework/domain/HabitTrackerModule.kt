package com.rc.android.homework.domain

import android.content.Context
import com.rc.android.homework.data.HabitRepository
import dagger.Module
import dagger.Provides

@Module
class HabitTrackerModule(private val context: Context) {

    @Provides
    fun provideHabitRepository(): HabitRepositoryI = HabitRepository(context)
}