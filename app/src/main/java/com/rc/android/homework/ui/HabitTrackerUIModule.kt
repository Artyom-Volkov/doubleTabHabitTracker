package com.rc.android.homework.ui

import com.rc.android.homework.domain.HabitRepositoryI
import com.rc.android.homework.domain.HabitTracker
import dagger.Module
import dagger.Provides

@Module
class HabitTrackerUIModule() {

    @Provides
    fun provideHabitTracker(habitRepository: HabitRepositoryI): HabitTracker = HabitTracker(habitRepository)
}