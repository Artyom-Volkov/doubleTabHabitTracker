package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitTracker
import kotlinx.coroutines.launch

class HabitListsViewModel(habitTracker: HabitTracker) : ViewModel() {

    private val habitListRepository: LiveData<List<Habit>> = habitTracker.habits.asLiveData()

    private val mutableHabitList: MutableLiveData<List<Habit>> = MutableLiveData()
    private var habitNameFilter = ""

    val habitList : LiveData<List<Habit>> = mutableHabitList

    private val habitRepositoryObserver: Observer<List<Habit>> =
        Observer<List<Habit>> {
                list -> list?.let {
                    updateHabitList(list)
                }
        }

    init {
        habitListRepository.observeForever(habitRepositoryObserver)

        viewModelScope.launch{
            habitTracker.updateLocalDatabaseFromServer()
        }
    }

    override fun onCleared() {
        habitListRepository.removeObserver(habitRepositoryObserver)
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
        habitListRepository.value?.let {
            updateHabitList(it)
        }
    }
}