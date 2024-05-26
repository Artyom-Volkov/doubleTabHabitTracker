package com.rc.android.habitrepository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rc.android.habittracker.Habit

@Database(entities = [Habit::class], version = 2, exportSchema = false)
@TypeConverters(HabitConverters::class)
abstract class HabitRoomDatabase : RoomDatabase() {
    abstract fun getHabitDAO(): HabitDAO
}