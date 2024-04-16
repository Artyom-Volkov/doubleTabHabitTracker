package com.rc.android.homework.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitRepository
import com.rc.android.homework.room.HabitDAO

class HabitListsViewModel(context: Context) : ViewModel() {

    private val habitRepository = HabitRepository(context)

    private val mutableHabitList: MutableLiveData<List<Habit>> = MutableLiveData()
    private var habitNameFilter = ""

    val habitList : LiveData<List<Habit>> = mutableHabitList

    init {

        HabitRepository.setListener(object : HabitRepository.Listener{
            override fun onHabitAdded(habits : List<Habit>) {
                updateHabitList(habits)
            }

            override fun onHabitReplaced(position: Int, habits : List<Habit>) {
                updateHabitList(habits)
            }
        })

        updateHabitList(HabitRepository.habits)
    }

    override fun onCleared() {
        HabitRepository.unsetListener()
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
        updateHabitList(HabitRepository.habits)
    }
}