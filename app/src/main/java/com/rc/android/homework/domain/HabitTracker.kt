package com.rc.android.homework.domain

class HabitTracker(private val habitRepository: HabitRepositoryI) {

    fun getHabit(id: Int): Habit = habitRepository.getHabit(id)

    suspend fun add(habit: Habit) = habitRepository.add(habit)

    suspend fun replace(habit: Habit) = habitRepository.replace(habit)
}