package com.rc.android.homework.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.rc.android.homework.Habit

@Dao
interface HabitDAO {

    @Insert(onConflict = REPLACE)
    fun add(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE id=(:id)")
    fun getHabitById(id: Int): Habit
}