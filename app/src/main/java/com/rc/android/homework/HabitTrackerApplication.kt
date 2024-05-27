package com.rc.android.homework

import android.app.Application
import com.rc.android.homework.ui.HabitTrackerUIModule

class HabitTrackerApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        //appComponent = DaggerAppComponent.create()
        appComponent = DaggerAppComponent
            .builder()
            .habitTrackerUIModule(HabitTrackerUIModule(this))
            .build()
    }


}