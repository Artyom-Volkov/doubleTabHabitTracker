package com.rc.android.habittracker

import kotlinx.coroutines.flow.Flow

interface HabitRepositoryI {

    val habits: Flow<List<Habit>>

    suspend fun updateLocalDatabaseFromServer()

    fun getHabit(id: Int): Habit

    suspend fun add(habit: Habit)

    suspend fun replace(habit: Habit)

    suspend fun habitDone(habitLocalId: Int)
}