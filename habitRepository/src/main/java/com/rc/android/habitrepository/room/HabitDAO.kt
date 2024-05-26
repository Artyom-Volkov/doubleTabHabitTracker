package com.rc.android.habitrepository.room

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.rc.android.habittracker.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDAO {

    @Insert(onConflict = REPLACE)
    suspend fun add(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE id=(:id)")
    fun getHabitById(id: Int): Habit

    @Query("SELECT * FROM habits WHERE server_uid=(:uid)")
    suspend fun getHabitByServerId(uid: String): Habit?
}