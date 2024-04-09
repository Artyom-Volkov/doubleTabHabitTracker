package com.rc.android.homework.ui.viewmodels

import com.rc.android.homework.HabitFreq
import com.rc.android.homework.HabitTimePeriod

class HabitFreqEditing (habitFreq: HabitFreq?) {

    var executionNumber: Int? = null
    var countTimePeriod: Int? = null
    var timePeriod: HabitTimePeriod? = null

    init {
        habitFreq?.let {
            executionNumber = it.executionNumber
            countTimePeriod = it.countTimePeriod
            timePeriod = it.timePeriod
        }
    }

    public fun toHabitFreq(): HabitFreq? {
        if (executionNumber != null && countTimePeriod != null && timePeriod != null){
            return HabitFreq(executionNumber!!, countTimePeriod!!, timePeriod!!)
        }
        else
            return null
    }
}