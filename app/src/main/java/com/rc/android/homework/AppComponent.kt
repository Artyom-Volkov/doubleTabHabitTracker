package com.rc.android.homework

import com.rc.android.homework.data.HabitRepositoryModule
import com.rc.android.homework.data.room.HabitDAO
import com.rc.android.homework.domain.HabitRepositoryI
import com.rc.android.homework.domain.HabitTrackerModule
import com.rc.android.homework.ui.HabitTrackerUIModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitTrackerUIModule::class, HabitTrackerModule::class, HabitRepositoryModule::class])
interface AppComponent {

    fun getHabitRepository(): HabitRepositoryI

    fun getHabitDAO(): HabitDAO
}