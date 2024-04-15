package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase

class HabitListsViewModel() : ViewModel() {

    private val mutableHabitList: MutableLiveData<List<Habit>> = MutableLiveData()
    private var habitNameFilter = ""

    val habitList : LiveData<List<Habit>> = mutableHabitList

    init {
        HabitDatabase.setListener(object : HabitDatabase.Listener{
            override fun onHabitAdded(habits : List<Habit>) {
                updateHabitList(habits)
            }

            override fun onHabitReplaced(position: Int, habits : List<Habit>) {
                updateHabitList(habits)
            }
        })

        updateHabitList(HabitDatabase.habits)
    }

    override fun onCleared() {
        HabitDatabase.unsetListener()
        super.onCleared()
    }

    private fun updateHabitList(habits : List<Habit>){

        if (habitNameFilter.isEmpty()){
            mutableHabitList.value = habits
        }
        else {
            mutableHabitList.value = habits.filter { it.name.startsWith(habitNameFilter) }
        }
    }

    fun habitNameFiltering(habitNameFilter: String){
        this.habitNameFilter = habitNameFilter
        updateHabitList(HabitDatabase.habits)
    }
}