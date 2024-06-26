package com.rc.android.habitrepository.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.rc.android.habitrepository.server.capsule.HabitCapsule
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDAO {

    @Insert(onConflict = REPLACE)
    suspend fun add(habit: HabitCapsule)

    @Update
    suspend fun update(habit: HabitCapsule)

    @Delete
    fun delete(habit: HabitCapsule)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitCapsule>>

    @Query("SELECT * FROM habits WHERE id=(:id)")
    fun getHabitById(id: Int): HabitCapsule

    @Query("SELECT * FROM habits WHERE server_uid=(:uid)")
    suspend fun getHabitByServerId(uid: String): HabitCapsule?
}