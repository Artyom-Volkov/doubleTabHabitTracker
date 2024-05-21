package com.rc.android.homework.domain

interface HabitRepositoryI {

    fun getHabit(id: Int): Habit

    suspend fun add(habit: Habit)

    suspend fun replace(habit: Habit)
}