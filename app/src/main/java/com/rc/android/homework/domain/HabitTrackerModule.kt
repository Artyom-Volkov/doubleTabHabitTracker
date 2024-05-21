package com.rc.android.homework.domain

import com.rc.android.homework.data.HabitRepository
import com.rc.android.homework.data.room.HabitDAO
import com.rc.android.homework.data.server.HabitTrackerNetworkClient
import dagger.Module
import dagger.Provides

@Module
class HabitTrackerModule() {

    @Provides
    fun provideHabitRepository(networkClient: HabitTrackerNetworkClient, habitDAO: HabitDAO): HabitRepositoryI
        = HabitRepository(networkClient , habitDAO)
}