package com.rc.android.homework.ui.fragment.habitListPagerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase
import com.rc.android.homework.R
import com.rc.android.homework.ui.fragment.HabitEditingFragment
import com.rc.android.homework.ui.fragment.habitListsFragment.HabitListsFragment
import com.rc.android.homework.ui.viewmodels.HabitListsViewModel
import com.rc.android.homework.ui.viewmodels.HabitListsViewModelFactory
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

    private lateinit var viewModel: HabitListsViewModel

    private var habitType: Habit.Type? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            habitType = Habit.Type.values().get( it.getInt(HABIT_TYPE))
        }

        val parentFragment = requireParentFragment()
        if (parentFragment is HabitListsFragment) {
            viewModel = ViewModelProvider(parentFragment, HabitListsViewModelFactory())
                .get(HabitListsViewModel::class.java)
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
        }

        viewModel.habitList.observe(viewLifecycleOwner) { list ->
            val filterList = list.filter { it.type == habitType }.toMutableList()
            habitRecyclerview.adapter = HabitAdapter(filterList, ::onHabitClicked)
        }

    }

    private fun onHabitClicked(habitType: Habit.Type, position: Int){

        val databasePosition = HabitDatabase.getPosition(habitType, position)

        val bundle = Bundle()
        bundle.putInt(HabitEditingFragment.HABIT_POSITION, databasePosition)
        findNavController().navigate(R.id.action_habitListsFragment_to_habitEditingFragment, bundle)
    }

}