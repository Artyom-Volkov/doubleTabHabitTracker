package com.rc.android.homework

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit (
    val name: String,
    val decr: String,
    val type: Type,
    val priority: Int,
    @Embedded val freq: HabitFreq,
    val color: Int,
    var server_uid: String = "") {

    @PrimaryKey(autoGenerate = true) var id: Int = 0

    enum class Type(val value: Int){
        USEFULL(0),
        HARMFULL(1)
    }
}