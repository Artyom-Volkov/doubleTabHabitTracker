package com.rc.android.homework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rc.android.homework.Habit

@Database(entities = [Habit::class], version = 1, exportSchema = false)
@TypeConverters(HabitConverters::class)
abstract class HabitRoomDatabase : RoomDatabase() {
    abstract fun getHabitDAO(): HabitDAO
}