package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit

class HabitEditingViewModel(private val habit: Habit?) : ViewModel() {

    private val mutableHabit: MutableLiveData<Habit?> = MutableLiveData()

    init {
        mutableHabit.value = habit
    }
}