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
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase
import com.rc.android.homework.R
import com.rc.android.homework.databinding.FragmentHabitEditingBinding
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModel
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModelFactory


class HabitEditingFragment : Fragment() {

    companion object {

        const val HABIT_POSITION = "HABIT_POSITION"
    }

    private var habit: Habit? = null
    private var position: Int? = null

    private lateinit var viewModel: HabitEditingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            position = args.getInt(HABIT_POSITION)
            position?.let {
                habit = HabitDatabase.getHabit(it)
            }
        }

        viewModel = ViewModelProvider(this, HabitEditingViewModelFactory(position, ::MakeShortToast))
            .get(HabitEditingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
    }

    private fun MakeShortToast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT)
            .apply { show() }
    }
}