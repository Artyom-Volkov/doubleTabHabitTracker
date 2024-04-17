package com.rc.android.homework

import android.content.Context
import androidx.lifecycle.LiveData
import com.rc.android.homework.room.HabitDAO
import com.rc.android.homework.room.HabitRoomDatabase

class HabitRepository(context: Context) {

    private val habitDAO: HabitDAO

    val habits: LiveData<List<Habit>>
        get() = habitDAO.getAllHabits()

    init {

        val habiRoomDatabase = HabitRoomDatabase.getInstance(context)
        habitDAO = habiRoomDatabase.getHabitDAO()
    }

    fun getHabit(id: Int): Habit = habitDAO.getHabitById(id)

    fun add(habit: Habit){

        habitDAO.add(habit)
    }

    fun replace(habit: Habit){

        habitDAO.update(habit)
    }
}