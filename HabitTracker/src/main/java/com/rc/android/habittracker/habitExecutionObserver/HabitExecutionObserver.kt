package com.rc.android.habittracker.habitExecutionObserver

import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitFreq
import com.rc.android.habittracker.HabitTimePeriod
import java.util.Calendar
import java.util.GregorianCalendar

class HabitExecutionObserver(
    val onHabitExecutionLimitHasBeenReached: (Habit.Type) -> Unit,
    val onHabitExecutionLimitHasNotBeenReached: (Habit.Type, Int) -> Unit) {

    fun checkExecution(habit: Habit){

        val requiredHabitExecutionNumber = habit.freq.executionNumber

        val dateList: MutableList<Calendar> =
            habit.doneDateList.map { timeInMillis ->
                GregorianCalendar().apply { this.timeInMillis = timeInMillis }
            }.toMutableList()

        if (dateList.isEmpty()) {
            onHabitExecutionLimitHasNotBeenReached(habit.type, requiredHabitExecutionNumber)
            return
        }

        val lastDoneDate = dateList[dateList.size - 1]

        val calculatedPeriodStart = getCalculatedPeriodStart(lastDoneDate, habit.freq).time

        dateList.filter {
            dateTime -> dateTime.time.after(calculatedPeriodStart)
        }
        val habitExecutionNumber = dateList.size

        if (habitExecutionNumber >= requiredHabitExecutionNumber){
            onHabitExecutionLimitHasBeenReached(habit.type)
        } else {
            onHabitExecutionLimitHasNotBeenReached(habit.type, requiredHabitExecutionNumber - habitExecutionNumber)
        }
    }

    private fun getCalculatedPeriodStart(lastDoneDate: Calendar, habitFreq: HabitFreq): Calendar{
        val lastDoneDateCopy = GregorianCalendar().apply { timeInMillis = lastDoneDate.timeInMillis }

        habitFreq.let{
            when (it.timePeriod){
                HabitTimePeriod.MINUTE -> lastDoneDateCopy.add(Calendar.MINUTE, - it.countTimePeriod)
                HabitTimePeriod.HOUR -> lastDoneDateCopy.add(Calendar.HOUR, - it.countTimePeriod)
                HabitTimePeriod.DAY -> lastDoneDateCopy.add(Calendar.DAY_OF_YEAR, - it.countTimePeriod)
                HabitTimePeriod.WEEK -> lastDoneDateCopy.add(Calendar.WEEK_OF_YEAR, - it.countTimePeriod)
                HabitTimePeriod.MONTH -> lastDoneDateCopy.add(Calendar.MONTH, - it.countTimePeriod)
                HabitTimePeriod.YEAR -> lastDoneDateCopy.add(Calendar.YEAR, - it.countTimePeriod)
            }
        }
        return lastDoneDateCopy
    }
}