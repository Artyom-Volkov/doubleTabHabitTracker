package com.rc.android.habittracker.habitExecutionObserver

import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitFreq
import com.rc.android.habittracker.HabitTimePeriod
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class HabitExecutionObserver(
    val onHabitExecutionLimitHasBeenReached: (Habit.Type) -> Unit,
    val onHabitExecutionLimitHasNotBeenReached: (Habit.Type, Int) -> Unit) {

    fun checkExecution(habit: Habit){

        val requiredHabitExecutionNumber = habit.freq.executionNumber

        val dateList: List<Date> =
            habit.doneDateList.map { timeInMillis ->
                Date(timeInMillis)
            }.toList()

        if (dateList.isEmpty()) {
            onHabitExecutionLimitHasNotBeenReached(habit.type, requiredHabitExecutionNumber)
            return
        }

        val lastDoneDate = dateList[dateList.size - 1]

        val calculatedPeriodStart = getCalculatedPeriodStart(lastDoneDate, habit.freq)

        val filterDateList = dateList.filter {
            dateTime -> dateTime.after(calculatedPeriodStart)
        }
        val habitExecutionNumber = filterDateList.size

        if (habitExecutionNumber >= requiredHabitExecutionNumber){
            onHabitExecutionLimitHasBeenReached(habit.type)
        } else {
            onHabitExecutionLimitHasNotBeenReached(habit.type, requiredHabitExecutionNumber - habitExecutionNumber)
        }
    }

    private fun getCalculatedPeriodStart(lastDoneDate: Date, habitFreq: HabitFreq): Date{
        val lastDoneDateCalender = GregorianCalendar().apply { timeInMillis = lastDoneDate.time }

        habitFreq.let{

            val rv = 5

            when (it.timePeriod){
                HabitTimePeriod.MINUTE -> lastDoneDateCalender.add(Calendar.MINUTE, - it.countTimePeriod)
                HabitTimePeriod.HOUR -> lastDoneDateCalender.add(Calendar.HOUR, - it.countTimePeriod)
                HabitTimePeriod.DAY -> lastDoneDateCalender.add(Calendar.DAY_OF_YEAR, - it.countTimePeriod)
                HabitTimePeriod.WEEK -> lastDoneDateCalender.add(Calendar.WEEK_OF_YEAR, - it.countTimePeriod)
                HabitTimePeriod.MONTH -> lastDoneDateCalender.add(Calendar.MONTH, - it.countTimePeriod)
                HabitTimePeriod.YEAR -> lastDoneDateCalender.add(Calendar.YEAR, - it.countTimePeriod)
            }
        }
        return lastDoneDateCalender.time
    }
}