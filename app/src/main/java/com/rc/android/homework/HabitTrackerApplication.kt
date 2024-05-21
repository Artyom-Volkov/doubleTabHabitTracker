package com.rc.android.homework

import android.app.Application
import com.rc.android.homework.data.HabitRepositoryModule

class HabitTrackerApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        //appComponent = DaggerAppComponent.create()
        appComponent = DaggerAppComponent
            .builder()
            .habitRepositoryModule(HabitRepositoryModule(this))
            .build()
    }


}