package com.rc.android.homework.ui.fragment.habitListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitTracker
import com.rc.android.homework.HabitTrackerApplication
import com.rc.android.homework.R
import com.rc.android.homework.databinding.FragmentHabitListBinding
import com.rc.android.homework.ui.fragment.HabitEditingFragment
import com.rc.android.homework.ui.fragment.habitListsFragment.HabitListsFragment
import com.rc.android.homework.ui.viewmodels.HabitListsViewModel
import com.rc.android.homework.ui.viewmodels.HabitListsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HabitListsViewModel

    @Inject
    lateinit var habitTracker: HabitTracker

    private var habitType: Habit.Type? = null

    private val habitAdapter: HabitAdapter = HabitAdapter(::onHabitEditingClicked, ::onHabitDoneClicked)

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
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.habitRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@HabitListFragment.context)
            adapter = habitAdapter
        }

        viewModel.habitList.observe(viewLifecycleOwner) { list ->
            val filterList = list.filter { it.type == habitType }
            habitAdapter.setHabitList(filterList)
        }

        habitTracker.habitExecutionLimitHasNotBeenReached.observe(viewLifecycleOwner){ message ->
            val stringFormat = when(message.habitType){
                Habit.Type.USEFULL -> getString(R.string.usefull_habit_execution_limit_has_not_been_reached_message)
                Habit.Type.HARMFULL -> getString(R.string.harmfull_habit_execution_limit_has_not_been_reached_message)
            }
            val messageStr = String.format(stringFormat, message.remainingExecutionCount)
            makeShortToast(messageStr)
        }
        habitTracker.habitExecutionLimitHasBeenReachedMessage.observe(viewLifecycleOwner){habitType ->
            when(habitType){
                Habit.Type.USEFULL -> makeShortToast(R.string.usefull_habit_execution_limit_has_been_reached_message)
                Habit.Type.HARMFULL -> makeShortToast(R.string.harmfull_habit_execution_limit_has_been_reached_message)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onHabitEditingClicked(habitId: Int){

        val bundle = Bundle()
        bundle.putInt(HabitEditingFragment.HABIT_ID, habitId)
        findNavController().navigate(R.id.action_habitListsFragment_to_habitEditingFragment, bundle)
    }

    private fun onHabitDoneClicked(habitId: Int){

        lifecycleScope.launch(Dispatchers.IO) {
            habitTracker.habitDone(habitId)
        }
    }

    private fun makeShortToast(str: String){
        Toast.makeText(context, str, Toast.LENGTH_LONG)
            .apply { show() }
    }
    private fun makeShortToast(stringId: Int){
        Toast.makeText(context, stringId, Toast.LENGTH_LONG)
            .apply { show() }
    }
}