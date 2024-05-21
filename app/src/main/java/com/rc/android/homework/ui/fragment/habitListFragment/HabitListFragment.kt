package com.rc.android.homework.ui.fragment.habitListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rc.android.homework.HabitTrackerApplication
import com.rc.android.homework.R
import com.rc.android.homework.domain.Habit
import com.rc.android.homework.domain.HabitTracker
import com.rc.android.homework.ui.fragment.HabitEditingFragment
import com.rc.android.homework.ui.fragment.habitListsFragment.HabitListsFragment
import com.rc.android.homework.ui.viewmodels.HabitListsViewModel
import com.rc.android.homework.ui.viewmodels.HabitListsViewModelFactory
import kotlinx.android.synthetic.main.fragment_habit_list.*
import javax.inject.Inject

class HabitListFragment : Fragment() {

    companion object {

        const val HABIT_TYPE = "HABIT_TYPE"

        @JvmStatic
        fun newInstance(habitType: Habit.Type): HabitListFragment {

            val fragment = HabitListFragment()
            fragment.arguments = Bundle().apply {
                putInt(HABIT_TYPE, habitType.ordinal)
            }
            return fragment
        }
    }

    private lateinit var viewModel: HabitListsViewModel

    @Inject
    lateinit var habitTracker: HabitTracker

    private var habitType: Habit.Type? = null

    private val habitAdapter: HabitAdapter = HabitAdapter { habitId -> onHabitClicked(habitId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            habitType = Habit.Type.values().get( it.getInt(HABIT_TYPE))
        }

        (requireActivity().application as HabitTrackerApplication).appComponent.inject(this)

        val parentFragment = requireParentFragment()
        if (parentFragment is HabitListsFragment) {
            viewModel = ViewModelProvider(parentFragment, HabitListsViewModelFactory(habitTracker))
                .get(HabitListsViewModel::class.java)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_habit_list, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@HabitListFragment.context)
            adapter = habitAdapter
        }

        viewModel.habitList.observe(viewLifecycleOwner) { list ->
            val filterList = list.filter { it.type == habitType }
            habitAdapter.setHabitList(filterList)
        }

    }

    private fun onHabitClicked(habitId: Int){

        val bundle = Bundle()
        bundle.putInt(HabitEditingFragment.HABIT_ID, habitId)
        findNavController().navigate(R.id.action_habitListsFragment_to_habitEditingFragment, bundle)
    }

}