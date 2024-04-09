package com.rc.android.homework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase
import com.rc.android.homework.R
import com.rc.android.homework.databinding.FragmentHabitEditingBinding
import com.rc.android.homework.ui.fragment.habitListsFragment.HabitListsFragment
import com.rc.android.homework.ui.viewmodels.HabitEditing
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModel
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModelFactory
import kotlinx.android.synthetic.main.fragment_habit_editing.*


class HabitEditingFragment : Fragment() {

    companion object {

        public const val HABIT_POSITION = "HABIT_POSITION"

        @JvmStatic
        fun newInstance() =
            HabitEditingFragment().apply {

            }

        fun newInstance(habit: Habit, position: Int): HabitEditingFragment {
            val fragment = HabitEditingFragment()
            val bundle = Bundle()
            //bundle.putParcelable(Habit::class.simpleName, habit)
            bundle.putInt(HABIT_POSITION, position)
            fragment.arguments = bundle
            return fragment
        }

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

        viewModel = ViewModelProvider(this, HabitEditingViewModelFactory(position))
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

        saveHabitButton.setOnClickListener{ saveHabitButtonClicked() }

        executionNumberEditText.addTextChangedListener{
            viewModel.executionNumberEditing(it.toString())
        }
        countTimePeriodEditText.addTextChangedListener {
            viewModel.countTimePeriodEditing(it.toString())
        }
        timePeriodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.timePeriodSpinnerChanged(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    private fun MakeShortToast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT)
            .apply { show() }
    }

    private fun saveHabitButtonClicked(){

        val habitEditing : HabitEditing? = viewModel.habit.value

        habitEditing?.run {
            if (name.isNullOrEmpty()){
                MakeShortToast("Укажите название привычки")
                return
            }

            if (type == null){
                MakeShortToast("Укажите тип привычки")
                return
            }

            if (freq.executionNumber == null){
                MakeShortToast("Укажите кол-во выполнения привычки")
                return
            }

            if (freq.countTimePeriod == null){
                MakeShortToast("Укажите периодичность привычки")
                return
            }

            val habit: Habit? = getHabit()
            if (habit != null){
                if (position == null){
                    addNewHabit(habit)
                } else {
                    habitEdited(position!!, habit)
                }
            }
        }
    }

    fun addNewHabit(habit: Habit){

        HabitDatabase.add(habit)

        val habitListsFragment : HabitListsFragment? = parentFragmentManager.findFragmentByTag(HabitListsFragment.TAG) as HabitListsFragment?

        habitListsFragment?.onHabitAdded()
        findNavController().navigate(R.id.action_habitEditingFragment_to_habitListsFragment)

    }

    fun habitEdited(position: Int, habit: Habit){
        HabitDatabase.replace(position, habit)

        val habitListsFragment : HabitListsFragment? = parentFragmentManager.findFragmentByTag(HabitListsFragment.TAG) as HabitListsFragment?

        habitListsFragment?.HabitEdited(position)
        findNavController().navigate(R.id.action_habitEditingFragment_to_habitListsFragment)
    }
}