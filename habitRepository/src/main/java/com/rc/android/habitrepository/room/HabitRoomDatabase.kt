package com.rc.android.habitrepository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rc.android.habitrepository.server.capsule.HabitCapsule

@Database(entities = [HabitCapsule::class], version = 3, exportSchema = false)
@TypeConverters(HabitConverters::class)
abstract class HabitRoomDatabase : RoomDatabase() {
    abstract fun getHabitDAO(): HabitDAO
}