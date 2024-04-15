package com.rc.android.homework.room

import androidx.room.*
import com.rc.android.homework.Habit

@Dao
interface HabitDAO {

    @Insert
    fun add(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): List<Habit>
}