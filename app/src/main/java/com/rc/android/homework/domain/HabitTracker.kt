package com.rc.android.homework.domain

import kotlinx.coroutines.flow.Flow

class HabitTracker(private val habitRepository: HabitRepositoryI) {

    val habits: Flow<List<Habit>>
        get() = habitRepository.habits

    fun getHabit(id: Int): Habit = habitRepository.getHabit(id)

    suspend fun updateLocalDatabaseFromServer() = habitRepository.updateLocalDatabaseFromServer()

    suspend fun add(habit: Habit) = habitRepository.add(habit)

    suspend fun replace(habit: Habit) = habitRepository.replace(habit)

    fun habitDone(habit: Habit) {//TODO
    }
}