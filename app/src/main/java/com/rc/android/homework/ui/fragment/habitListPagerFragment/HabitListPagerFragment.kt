package com.rc.android.homework.ui.fragment.habitListPagerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase
import com.rc.android.homework.R
import com.rc.android.homework.ui.fragment.HabitEditingFragment
import kotlinx.android.synthetic.main.fragment_habit_list_pager.*

class HabitListPagerFragment : Fragment() {

    companion object {

        const val HABIT_TYPE = "HABIT_TYPE"

        @JvmStatic
        fun newInstance(habitType: Habit.Type) {
            val bundle = Bundle()
            bundle.putInt(HABIT_TYPE, habitType.ordinal)
        }

    }

    private var habitType: Habit.Type? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            habitType = Habit.Type.values().get( it.getInt(HABIT_TYPE))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_habit_list_pager, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@HabitListPagerFragment.context)

            val habits = when (habitType) {
                Habit.Type.HARMFULL -> HabitDatabase.harmfullHabits
                else -> HabitDatabase.usefullHabits
            }

            adapter = HabitAdapter(habits, ::onHabitClicked )
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()

    }

    private fun onHabitClicked(habitType: Habit.Type, position: Int){

        habitType.let {
            val habits = when(it) {
                Habit.Type.USEFULL -> HabitDatabase.usefullHabits
                Habit.Type.HARMFULL -> HabitDatabase.harmfullHabits
            }

            val habit = habits.get(position)

            val bundle = Bundle()
            bundle.putParcelable(Habit::class.simpleName, habit)
            bundle.putInt(HabitEditingFragment.HABIT_POSITION, position)
            findNavController().navigate(R.id.action_habitListsFragment_to_habitEditingFragment, bundle)
        }
    }

}