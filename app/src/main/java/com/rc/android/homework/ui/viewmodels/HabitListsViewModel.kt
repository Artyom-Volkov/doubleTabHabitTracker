package com.rc.android.homework.ui.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.rc.android.homework.data.HabitRepository
import com.rc.android.homework.domain.Habit
import kotlinx.coroutines.launch

class HabitListsViewModel(context: Context) : ViewModel() {

    private val habitListRepository: LiveData<List<Habit>> = HabitRepository(context).habits.asLiveData()

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
            HabitRepository(context).updateLocalDatabaseFromServer()
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