package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase

class HabitListsViewModel(private val habitType: Habit.Type) : ViewModel() {

    private val mutableHabitList =
        MutableLiveData<MutableList<Habit>>().apply {
            value = HabitDatabase.habits.filter { it.type == habitType }.toMutableList()
        }

    val filterHabitList : MutableLiveData<MutableList<Habit>> = MutableLiveData()
}