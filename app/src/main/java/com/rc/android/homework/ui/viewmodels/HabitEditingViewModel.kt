package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitRepository
import com.rc.android.homework.HabitTimePeriod

class HabitEditingViewModel(private val position: Int?,
                            private val onMakeShortToast : (String) -> Unit ) : ViewModel() {

    private val mutableHabit: MutableLiveData<HabitEditing> = MutableLiveData()
    private val mutableIsHaveBeenHabitSaved: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }

    val habit: LiveData<HabitEditing> = mutableHabit
    val isHaveBeenHabitSaved: LiveData<Boolean> = mutableIsHaveBeenHabitSaved

    init {
        mutableHabit.value = if (position == null) {
            HabitEditing(null)
        } else {
            HabitEditing( HabitRepository.getHabit(position))
        }
    }

    fun usefulHabitRadioButtonClicked(){
        mutableHabit.value?.type = Habit.Type.USEFULL
    }

    fun harmfulHabitRadioButtonClicked(){
        mutableHabit.value?.type = Habit.Type.HARMFULL
    }

    fun timePeriodSpinnerChanged(position: Int){
        mutableHabit.value?.freq?.timePeriod = HabitTimePeriod.values()[position]
    }

    fun saveHabitButtonClicked(){

        val habitEditing : HabitEditing? = habit.value

        habitEditing?.run {
            if (name.isNullOrEmpty()){
                onMakeShortToast("Укажите название привычки")
                return
            }

            if (type == null){
                onMakeShortToast("Укажите тип привычки")
                return
            }

            if (freq.executionNumber == null){
                onMakeShortToast("Укажите кол-во выполнения привычки")
                return
            }

            if (freq.countTimePeriod == null){
                onMakeShortToast("Укажите периодичность привычки")
                return
            }

            val habit: Habit? = getHabit()
            if (habit != null){
                if (position == null){
                    addNewHabit(habit)
                } else {
                    habitEdited(position!!, habit)
                }
            }
        }
    }

    private fun addNewHabit(habit: Habit){

        HabitRepository.add(habit)

        mutableIsHaveBeenHabitSaved.value = true
    }

    private fun habitEdited(position: Int, habit: Habit){

        HabitRepository.replace(position, habit)

        mutableIsHaveBeenHabitSaved.value = true
    }

}