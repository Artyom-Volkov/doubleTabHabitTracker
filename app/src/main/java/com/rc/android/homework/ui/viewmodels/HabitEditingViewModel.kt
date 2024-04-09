package com.rc.android.homework.ui.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase
import com.rc.android.homework.HabitTimePeriod

class HabitEditingViewModel(private val position: Int?,
                            private val onMakeToast: (String)-> Unit ) : ViewModel() {

    private val mutableHabit: MutableLiveData<HabitEditing> = MutableLiveData()

    public val habit: LiveData<HabitEditing> = mutableHabit

    init {
        mutableHabit.value = if (position == null) {
            HabitEditing(null)
        } else {
            HabitEditing( HabitDatabase.getHabit(position))
        }
    }

    public fun usefulHabitRadioButtonClicked(v: View){
        mutableHabit.value?.type = Habit.Type.USEFULL
    }

    public fun harmfulHabitRadioButtonClicked(v: View){
        mutableHabit.value?.type = Habit.Type.HARMFULL
    }

    public fun executionNumberEditing(executionNumberStr: String){
        mutableHabit.value?.freq?.executionNumber = executionNumberStr.toInt()
    }

    public fun countTimePeriodEditing(countTimePeriodStr: String){
        mutableHabit.value?.freq?.countTimePeriod = countTimePeriodStr.toInt()
    }
    public fun timePeriodSpinnerChanged(position: Int){
        mutableHabit.value?.freq?.timePeriod = HabitTimePeriod.values()[position]
    }

    public fun saveHabitButtonClicked(v: View){

        habit.value?.run {
            if (name.isNullOrEmpty()){
                onMakeToast("Укажите название привычки")
                return
            }

            if (type == null){
                onMakeToast("Укажите тип привычки")
                return
            }
            
            if (freq.executionNumber == null){
                onMakeToast("Укажите кол-во выполнения привычки")
                return
            }
            
            if (freq.countTimePeriod == null){
                onMakeToast("Укажите периодичность привычки")
                return
            }

            val habit = getHabit()
            habit?.let { habit ->  
                
            }
        }


    }

}