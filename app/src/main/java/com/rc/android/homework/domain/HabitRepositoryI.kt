package com.rc.android.homework.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepositoryI {

    val habits: Flow<List<Habit>>

    suspend fun updateLocalDatabaseFromServer()

    fun getHabit(id: Int): Habit

    suspend fun add(habit: Habit)

    suspend fun replace(habit: Habit)
}