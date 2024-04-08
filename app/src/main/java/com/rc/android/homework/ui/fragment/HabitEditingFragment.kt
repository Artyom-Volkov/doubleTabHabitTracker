package com.rc.android.homework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rc.android.homework.*
import com.rc.android.homework.ui.fragment.habitListsFragment.HabitListsFragment
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

    //private var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            position = args.getInt(HABIT_POSITION)
            position?.let {
                habit = HabitDatabase.getHabit(it)
            }

            viewModel = ViewModelProvider(this, HabitEditingViewModelFactory(position))
                .get(HabitEditingViewModel::class.java)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit_editing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habit?.let {
            nameEditText.text.append(it.name)
            prioritySpinner.setSelection(it.priority - 1)
            executionNumberEditText.text.append(it.freq.executionNumber.toString())
            countTimePeriodEditText.text.append(it.freq.countTimePeriod.toString())

            val position = it.freq.timePeriod.ordinal
            timePeriodSpinner.setSelection(position)

            habitDecrEditText.text.append(it.decr)

            when(it.type){
                Habit.Type.USEFULL -> usefulHabitRadioButton.isChecked = true
                Habit.Type.HARMFULL -> harmfulHabitRadioButton.isChecked = true
            }
        }

        saveHabitButton.setOnClickListener{ saveHabitButtonClicked() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun saveHabitButtonClicked(){

        if (nameEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите название привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (habitTypeRadioGroup.checkedRadioButtonId == -1){
            Toast.makeText(context, "Укажите тип привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (executionNumberEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите кол-во выполнения привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (countTimePeriodEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите периодичность привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        val freqHabit = HabitFreq(executionNumberEditText.text.toString().toUInt(),
            countTimePeriodEditText.text.toString().toUInt(),
            HabitTimePeriod.values().get(timePeriodSpinner.selectedItemPosition)
        )

        val habit = Habit(
            name = nameEditText.text.toString(),
            decr = habitDecrEditText.text.toString(),
            type = when(habitTypeRadioGroup.checkedRadioButtonId){
                usefulHabitRadioButton.id -> Habit.Type.USEFULL
                else -> Habit.Type.HARMFULL
            },
            priority = prioritySpinner.selectedItem.toString().toInt(),
            freq = freqHabit,
            -1
        )

        if (this.habit != null){
            position?.let { position ->
                habitEdited(position, habit)
            }
            /*if (position!= null)
                habitEdited(habit, position!!, habit.type != this.habit!!.type)*/
        }
        else{
            addNewHabit(habit)
        }
    }

    fun addNewHabit(habit: Habit){

        /*habit.let {
            val habits = when (it.type) {
                Habit.Type.HARMFULL -> HabitDatabase.harmfullHabits
                else -> HabitDatabase.usefullHabits
            }
            habits.add(it)
        }*/
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