package com.rc.android.homework

object HabitDatabase {
    val usefullHabits: MutableList<Habit> = mutableListOf<Habit>()
    val harmfullHabits: MutableList<Habit> = mutableListOf<Habit>()

    val habits: MutableList<Habit> = mutableListOf<Habit>()

    /*fun getList(habitType: Habit.Type) : MutableList<Habit> {
        return habits.filter { it.type == habitType }.toMutableList()
    }*/
    fun getList(habitType: Habit.Type) : MutableList<Habit> = habits.filter { it.type == habitType }.toMutableList()
}