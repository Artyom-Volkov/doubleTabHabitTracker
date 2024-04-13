package com.rc.android.homework.ui.fragment.habitListsFragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rc.android.homework.Habit
import com.rc.android.homework.ui.fragment.habitListPagerFragment.HabitListPagerFragment

class HabitListsFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Habit.Type.values().size

    override fun createFragment(position: Int): Fragment {

        val habitType: Habit.Type = when (position){
            0 -> Habit.Type.USEFULL
            1 -> Habit.Type.HARMFULL
            else -> {
                Habit.Type.USEFULL
            }
        }
        return HabitListPagerFragment.newInstance(habitType)
    }
}