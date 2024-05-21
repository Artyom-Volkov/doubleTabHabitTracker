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
import com.rc.android.homework.domain.Habit
import com.rc.android.homework.data.HabitRepository
import com.rc.android.homework.R
import com.rc.android.homework.databinding.FragmentHabitEditingBinding
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModel
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModelFactory
import kotlinx.android.synthetic.main.fragment_habit_editing.*


class HabitEditingFragment : Fragment() {

    companion object {

        const val HABIT_ID = "HABIT_ID"
    }

    private var habit: Habit? = null
    private var habitId: Int? = null

    private lateinit var viewModel: HabitEditingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            habitId = args.getInt(HABIT_ID)
            habitId?.let {

                val habitRepository = HabitRepository(requireContext())
                habit = habitRepository.getHabit(it)
            }
        }

        viewModel = ViewModelProvider(this, HabitEditingViewModelFactory(requireContext(), habitId, ::makeShortToast))
            .get(HabitEditingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentHabitEditingBinding>(inflater,
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
            saveHabitButton.isEnabled = it
        }
    }

    private fun makeShortToast(stringId: Int){
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT)
            .apply { show() }
    }
}