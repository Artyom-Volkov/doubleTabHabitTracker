package com.rc.android.homework.ui.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase
import com.rc.android.homework.HabitTimePeriod

class HabitEditingViewModel(private val position: Int? ) : ViewModel() {

    private val mutableHabit: MutableLiveData<HabitEditing> = MutableLiveData()

    public val habit: LiveData<HabitEditing> = mutableHabit

    init {
        mutableHabit.value = if (position == null) {
            HabitEditing(null)
        } else {
            HabitEditing( HabitDatabase.getHabit(position))
        }
    }

    fun usefulHabitRadioButtonClicked(v: View){
        mutableHabit.value?.type = Habit.Type.USEFULL
    }

    fun harmfulHabitRadioButtonClicked(v: View){
        mutableHabit.value?.type = Habit.Type.HARMFULL
    }

    fun executionNumberEditing(executionNumberStr: String){
        mutableHabit.value?.freq?.executionNumber = executionNumberStr.toIntOrNull()
    }

    fun countTimePeriodEditing(countTimePeriodStr: String){
        mutableHabit.value?.freq?.countTimePeriod = countTimePeriodStr.toIntOrNull()
    }

    fun timePeriodSpinnerChanged(position: Int){
        mutableHabit.value?.freq?.timePeriod = HabitTimePeriod.values()[position]
    }


}