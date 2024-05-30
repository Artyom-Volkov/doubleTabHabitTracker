package com.rc.android.homework.ui.fragment.habitListsFragment

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitFreq
import com.rc.android.habittracker.HabitTimePeriod
import com.rc.android.homework.R
import com.rc.android.homework.ui.MainActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HabitListsFragmentTest {

    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun addNewHabitTest() {

        val habit = getTestHabit()

        addNewHabitToUI(habit)

        onView(withId(R.id.habitRecyclerview)).perform(ScrollToBottomAction())

        onView(withId(R.id.habitRecyclerview)).check(HabitRecyclerViewLastItemAssertion(habit))
    }

    fun getTestHabit(): Habit{

        val habitFreq = HabitFreq(4, 3, HabitTimePeriod.HOUR)

        return Habit("testName", "test", Habit.Type.USEFULL, 1, habitFreq, -1, emptyList())
    }

    private fun addNewHabitToUI(habit: Habit){

        onView(withId(R.id.addHabitFAB)).perform(click())

        onView(withId(R.id.nameEditText)).perform(typeText(habit.name)).perform(closeSoftKeyboard());

        onView(withId(R.id.prioritySpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(habit.priority.toString()))).perform(click())
        onView(withId(R.id.prioritySpinner)).check(matches(withSpinnerText(containsString(habit.priority.toString()))))

        val habitTypeRadioButtonId = when(habit.type){
            Habit.Type.USEFULL -> R.id.usefulHabitRadioButton
            Habit.Type.HARMFULL -> R.id.harmfulHabitRadioButton
        }
        onView(withId(habitTypeRadioButtonId)).perform(click())

        onView(withId(R.id.executionNumberEditText)).perform(typeText(habit.freq.executionNumber.toString())).perform(closeSoftKeyboard());
        onView(withId(R.id.countTimePeriodEditText)).perform(typeText(habit.freq.countTimePeriod.toString())).perform(closeSoftKeyboard());

        onView(withId(R.id.timePeriodSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("час"))).perform(click())//TODO: сделать более гибко

        onView(withId(R.id.habitDecrEditText)).perform(typeText(habit.decr)).perform(closeSoftKeyboard());

        onView(withId(R.id.saveHabitButton)).perform(click())
    }


}