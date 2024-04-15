package com.rc.android.homework

data class Habit (
    val name: String,
    val decr: String,
    val type: Type,
    val priority: Int,
    val freq: HabitFreq,
    val color: Int) {

    enum class Type(val value: Int){
        USEFULL(0),
        HARMFULL(1)
    }
}