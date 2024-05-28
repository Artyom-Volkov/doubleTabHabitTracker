package com.rc.android.habittracker

data class Habit (
    val name: String,
    val decr: String,
    val type: Type,
    val priority: Int,
    val freq: HabitFreq,
    val color: Int,
    val doneDateList: List<Long>,
    val id: Int = 0) {

    enum class Type(val value: Int){
        USEFULL(0),
        HARMFULL(1)
    }
}