package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase

class HabitListsViewModel() : ViewModel(), HabitDatabase.Listener {

    private val mutableHabitList: MutableLiveData<MutableList<Habit>> = MutableLiveData()
    private var habitNameFilter = ""

    val habitList : LiveData<MutableList<Habit>> = mutableHabitList

    init {
        HabitDatabase.setListener(this)

        updateHabitList()
    }

    override fun onCleared() {
        HabitDatabase.unsetListener()
        super.onCleared()
    }

    private fun updateHabitList(){

        if (habitNameFilter.isEmpty()){
            mutableHabitList.value = HabitDatabase.habits.toMutableList()
        }
        else {
            mutableHabitList.value =
                HabitDatabase.habits.filter { it.name.startsWith(habitNameFilter) }.toMutableList()
        }
    }

    override fun onHabitAdded() {
        updateHabitList()
    }

    override fun onHabitReplaced(position: Int) {
        updateHabitList()
    }

    public fun HabitNameFiltering(habitNameFilter: String){
        this.habitNameFilter = habitNameFilter
        updateHabitList()
    }
}