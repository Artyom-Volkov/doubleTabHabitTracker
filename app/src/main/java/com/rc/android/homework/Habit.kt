package com.rc.android.homework

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val name: String,
    val decr: String,
    val type: Type,
    val priority: Int,
    @Embedded val freq: HabitFreq,
    @Ignore val color: Int) {

    enum class Type(val value: Int){
        USEFULL(0),
        HARMFULL(1)
    }
}