package com.rc.android.homework

import android.app.Application
import com.rc.android.homework.data.HabitRepositoryModule
import com.rc.android.homework.domain.HabitTrackerModule

class HabitTrackerApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        //appComponent = DaggerAppComponent.create()
        appComponent = DaggerAppComponent
            .builder()
            .habitRepositoryModule(HabitRepositoryModule(this))
            .habitTrackerModule(HabitTrackerModule(this))
            .build()
    }


}