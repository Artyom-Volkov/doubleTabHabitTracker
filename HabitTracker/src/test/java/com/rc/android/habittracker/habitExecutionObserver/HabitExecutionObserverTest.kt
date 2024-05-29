package com.rc.android.habittracker.habitExecutionObserver

import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitFreq
import com.rc.android.habittracker.HabitTimePeriod
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.GregorianCalendar

class HabitExecutionObserverTest {

    private var habitExecutionObserver = HabitExecutionObserver(::onHabitExecutionLimitHasBeenReached,
        ::onHabitExecutionLimitHasNotBeenReached);

    private var remainingExecutionCountExpected: Int = 0

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkExecutionWithMinuteTimePeriod() {

        val habit: Habit = getTestHabitWithMinuteTimePeriod()
        habitExecutionObserver.checkExecution(habit)
    }

    private fun getTestHabitWithMinuteTimePeriod(): Habit{

        val habitFreq = HabitFreq(4, 10, HabitTimePeriod.MINUTE)

        val doneDateList: MutableList<Long> = mutableListOf()

        val startCalendar = GregorianCalendar(2017, Calendar.JANUARY , 25).apply {
            set(Calendar.MINUTE, 5)
        }
        doneDateList.add( startCalendar.timeInMillis )

        startCalendar.set(Calendar.MINUTE, 8)
        doneDateList.add( startCalendar.timeInMillis )

        startCalendar.set(Calendar.MINUTE, 10)
        doneDateList.add( startCalendar.timeInMillis )

        remainingExecutionCountExpected = 1

        return Habit("", "", Habit.Type.USEFULL, 0, habitFreq, -1, doneDateList.toList(), 0)
    }

    @Test
    fun checkExecutionWithYearTimePeriod() {

        val habit: Habit = getTestHabitWithYearTimePeriod()
        habitExecutionObserver.checkExecution(habit)
    }

    private fun getTestHabitWithYearTimePeriod(): Habit{

        val habitFreq = HabitFreq(5, 2, HabitTimePeriod.YEAR)

        val doneDateList: MutableList<Long> = mutableListOf()

        val calendar1 = GregorianCalendar(2017, Calendar.AUGUST , 29).apply {
            set(Calendar.MINUTE, 5)
        }
        doneDateList.add( calendar1.timeInMillis )

        val calendar2 = GregorianCalendar(2017, Calendar.OCTOBER , 4)
        doneDateList.add( calendar2.timeInMillis )

        val calendar3 = GregorianCalendar(2018, Calendar.FEBRUARY , 16)
        doneDateList.add( calendar3.timeInMillis )

        val calendar4 = GregorianCalendar(2019, Calendar.AUGUST, 30)
        doneDateList.add( calendar4.timeInMillis )

        remainingExecutionCountExpected = 2

        return Habit("", "", Habit.Type.USEFULL, 0, habitFreq, -1, doneDateList.toList(), 0)
    }


    private fun onHabitExecutionLimitHasBeenReached(habitType: Habit.Type){

    }

    private fun onHabitExecutionLimitHasNotBeenReached(habitType: Habit.Type, remainingExecutionCount: Int){
        Assert.assertEquals(remainingExecutionCountExpected, remainingExecutionCount)
    }

}