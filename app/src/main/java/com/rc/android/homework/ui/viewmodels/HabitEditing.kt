package com.rc.android.homework.ui.viewmodels

import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitFreq

class HabitEditing (habit: Habit?) {

    var name: String? = null
    var decr: String = ""
    var type: Habit.Type? = null
    var priority: Int? = null
    var freq: HabitFreqEditing = HabitFreqEditing(null)

    init {
        habit?.let {
            name = it.name
            decr = it.decr
            type = it.type
            priority = it.priority
            freq = HabitFreqEditing( it.freq)
        }

    }

    fun getHabit(): Habit? {

        var habit: Habit? = null

        val habitFreq: HabitFreq? = freq.toHabitFreq()

        if (name != null && type != null && priority!= null && habitFreq != null)
            habit = Habit(name!!, decr!!, type!!, priority!!, habitFreq, -1 )

        return habit
    }

}