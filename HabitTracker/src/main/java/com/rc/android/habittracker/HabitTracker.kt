package com.rc.android.habittracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rc.android.habittracker.habitExecutionObserver.HabitExecutionObserver
import kotlinx.coroutines.flow.Flow

class HabitTracker(private val habitRepository: HabitRepositoryI) {

    private val habitExecutionObserver = HabitExecutionObserver(::onHabitExecutionLimitHasBeenReached,
        ::onHabitExecutionLimitHasNotBeenReached)

    val habits: Flow<List<Habit>>
        get() = habitRepository.habits

    private val mutableHabitExecutionLimitHasBeenReachedMessage: MutableLiveData<Habit.Type> = MutableLiveData()
    private val mutableHabitExecutionLimitHasNotBeenReached: MutableLiveData<HabitExecutionLimitHasNotBeenReachedMessage> = MutableLiveData()

    val habitExecutionLimitHasBeenReachedMessage: LiveData<Habit.Type> = mutableHabitExecutionLimitHasBeenReachedMessage
    val habitExecutionLimitHasNotBeenReached: LiveData<HabitExecutionLimitHasNotBeenReachedMessage>
        = mutableHabitExecutionLimitHasNotBeenReached

    fun getHabit(id: Int): Habit = habitRepository.getHabit(id)

    suspend fun updateLocalDatabaseFromServer() = habitRepository.updateLocalDatabaseFromServer()

    suspend fun add(habit: Habit) = habitRepository.add(habit)

    suspend fun replace(habit: Habit) = habitRepository.replace(habit)

    suspend fun habitDone(habitId: Int) {

        val habit: Habit = habitRepository.habitDone(habitId)

        habitExecutionObserver.checkExecution(habit)
    }

    private fun onHabitExecutionLimitHasBeenReached(habitType: Habit.Type){
        mutableHabitExecutionLimitHasBeenReachedMessage.postValue(habitType)
    }

    private fun onHabitExecutionLimitHasNotBeenReached(habitType: Habit.Type, remainingExecutionCount: Int){

        val message = HabitExecutionLimitHasNotBeenReachedMessage(habitType, remainingExecutionCount)

        mutableHabitExecutionLimitHasNotBeenReached.postValue(message)
    }
}