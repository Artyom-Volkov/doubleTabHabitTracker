package com.rc.android.homework.ui.fragment.habitListsFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import com.rc.android.habittracker.Habit
import com.rc.android.homework.R
import com.rc.android.homework.ui.fragment.habitListFragment.HabitAdapter
import com.rc.android.homework.ui.fragment.habitListFragment.HabitViewHolder
import org.junit.Assert.assertEquals

class HabitRecyclerViewLastItemAssertion(private val lastHabit: Habit) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null)
            throw noViewFoundException

        val recyclerView: RecyclerView = view as RecyclerView

        val habitAdapter: HabitAdapter = recyclerView.adapter as HabitAdapter
        val lastPosition = habitAdapter.itemCount - 1

        val itemView: HabitViewHolder = recyclerView.findViewHolderForAdapterPosition(lastPosition) as HabitViewHolder

        itemView.habitCardBinding.let {
            assertEquals(lastHabit.name, it.habitNameTv.text.toString())

            val habitTypeStrExpected = when(lastHabit.type){
                Habit.Type.USEFULL -> view.context.getString(R.string.usefull_habit)
                Habit.Type.HARMFULL -> view.context.getString(R.string.harmfull_habit)
            }
            assertEquals(habitTypeStrExpected, it.habitTypeTv.text.toString())

            assertEquals(lastHabit.decr, it.habitDecriptionTv.text.toString())

            assertEquals(lastHabit.priority.toString(), it.habitPriorityTv.text.toString())

            val stringFormat = view.context.getString(R.string.habit_freq_string_format)
            val habitFreqStrExpected: String = with(lastHabit.freq){
                String.format(stringFormat, executionNumber.toString(), countTimePeriod.toString(), timePeriod.time)
            }
            assertEquals(habitFreqStrExpected, it.habitFreqTv.text.toString())
        }
    }
}