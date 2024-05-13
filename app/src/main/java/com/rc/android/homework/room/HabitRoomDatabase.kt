package com.rc.android.homework.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rc.android.homework.Habit

@Database(entities = [Habit::class], version = 2, exportSchema = false)
@TypeConverters(HabitConverters::class)
abstract class HabitRoomDatabase : RoomDatabase() {
    abstract fun getHabitDAO(): HabitDAO

    companion object {
        @Volatile
        private var INSTANCE: HabitRoomDatabase? = null

        fun getInstance(context: Context): HabitRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HabitRoomDatabase::class.java,
                        "habit_database")
                        .allowMainThreadQueries()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}