package com.rc.android.homework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitTracker
import com.rc.android.homework.HabitTrackerApplication
import com.rc.android.homework.R
import com.rc.android.homework.databinding.FragmentHabitEditingBinding
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModel
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModelFactory
import javax.inject.Inject


class HabitEditingFragment : Fragment() {

    companion object {

        const val HABIT_ID = "HABIT_ID"
    }

    private var habit: Habit? = null
    private var habitId: Int? = null

    private lateinit var binding: FragmentHabitEditingBinding

    private lateinit var viewModel: HabitEditingViewModel

    @Inject
    lateinit var habitTracker: HabitTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as HabitTrackerApplication).appComponent.inject(this)

        arguments?.let { args ->
            habitId = args.getInt(HABIT_ID)
            habitId?.let {

                //val habitRepository = HabitRepository(requireContext())
                habit = habitTracker.getHabit(it)
            }
        }

        viewModel = ViewModelProvider(this, HabitEditingViewModelFactory(habitTracker, habitId, ::makeShortToast))
            .get(HabitEditingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<FragmentHabitEditingBinding>(inflater,
            R.layout.fragment_habit_editing, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isHaveBeenHabitSaved.observe(viewLifecycleOwner){

            if (it == true){
                findNavController().popBackStack()
            }
        }
        viewModel.isSaveButtonEnabled.observe(viewLifecycleOwner){
            binding.saveHabitButton.isEnabled = it
        }
    }

    private fun makeShortToast(stringId: Int){
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT)
            .apply { show() }
    }
}