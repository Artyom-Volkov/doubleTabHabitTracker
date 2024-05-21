package com.rc.android.homework.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rc.android.homework.R
import com.rc.android.homework.data.HabitRepository
import com.rc.android.homework.domain.Habit
import com.rc.android.homework.domain.HabitTimePeriod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitEditingViewModel(
    context: Context,
    private val habitId: Int?,
    private val onMakeShortToast : (Int) -> Unit ) : ViewModel() {

    private val habitRepository = HabitRepository(context)

    private val mutableHabit: MutableLiveData<HabitEditing> = MutableLiveData()
    private val mutableIsSaveButtonEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = true }
    private val mutableIsHaveBeenHabitSaved: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }

    val habit: LiveData<HabitEditing> = mutableHabit
    val isSaveButtonEnabled: LiveData<Boolean> = mutableIsSaveButtonEnabled
    val isHaveBeenHabitSaved: LiveData<Boolean> = mutableIsHaveBeenHabitSaved

    init {
        mutableHabit.value = if (habitId == null) {
            HabitEditing(null)
        } else {
            HabitEditing( habitRepository.getHabit(habitId))
        }
    }

    fun habitTypeRadioButtonClicked(habitType: Habit.Type){
        mutableHabit.value?.type = habitType
    }

    fun timePeriodSpinnerChanged(position: Int){
        mutableHabit.value?.freq?.timePeriod = HabitTimePeriod.values()[position]
    }

    fun saveHabitButtonClicked(){

        val habitEditing : HabitEditing? = habit.value

        habitEditing?.run {
            if (name.isNullOrEmpty()){
                onMakeShortToast(R.string.warning_choose_habit_name)
                return
            }

            if (type == null){
                onMakeShortToast(R.string.warning_choose_habit_type)
                return
            }

            if (freq.executionNumber == null){
                onMakeShortToast(R.string.warning_choose_habit_execution_number)
                return
            }

            if (freq.countTimePeriod == null){
                onMakeShortToast(R.string.warning_choose_habit_count_time_period)
                return
            }

            val habit: Habit? = getHabit()
            if (habit != null){
                if (habitId == null){
                    addNewHabit(habit)
                } else {
                    habit.id = habitId
                    habit.server_uid = habitRepository.getHabit(habitId).server_uid
                    habitEdited(habit)
                }
            }
        }
    }

    private fun addNewHabit(habit: Habit){

        val job = viewModelScope.launch(Dispatchers.IO) {
            mutableIsSaveButtonEnabled.postValue(false)

            habitRepository.add(habit)
        }
        job.invokeOnCompletion { throwable ->
            if (throwable == null){
                mutableIsHaveBeenHabitSaved.postValue(true)
            }
            mutableIsSaveButtonEnabled.postValue(true)
        }
    }

    private fun habitEdited(habit: Habit){

        val job = viewModelScope.launch(Dispatchers.IO) {
            mutableIsSaveButtonEnabled.postValue(false)

            habitRepository.replace(habit)
        }
        job.invokeOnCompletion { throwable ->
            if (throwable == null){
                mutableIsHaveBeenHabitSaved.postValue(true)
            }
            mutableIsSaveButtonEnabled.postValue(true)
        }
    }

}