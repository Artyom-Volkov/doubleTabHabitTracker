package com.rc.android.homework

import com.rc.android.homework.data.HabitRepositoryModule
import com.rc.android.homework.domain.HabitTrackerModule
import com.rc.android.homework.ui.HabitTrackerUIModule
import com.rc.android.homework.ui.fragment.BottomSheetFragment
import com.rc.android.homework.ui.fragment.HabitEditingFragment
import com.rc.android.homework.ui.fragment.habitListFragment.HabitListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitTrackerUIModule::class, HabitTrackerModule::class, HabitRepositoryModule::class])
interface AppComponent {

    fun inject(habitListFragment: HabitListFragment)
    fun inject(bottomSheetFragment: BottomSheetFragment)
    fun inject(habitEditingFragment: HabitEditingFragment)
}