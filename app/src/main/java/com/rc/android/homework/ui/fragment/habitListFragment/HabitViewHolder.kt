package com.rc.android.homework.ui.fragment.habitListFragment

import androidx.recyclerview.widget.RecyclerView
import com.rc.android.homework.R
import com.rc.android.homework.databinding.HabitCardBinding
import com.rc.android.homework.domain.Habit

class HabitViewHolder(val habitCardBinding : HabitCardBinding,
                      val onHabitCardClicked: (Int) -> Unit,
                      val onHabitDoneBtnClicked: (Int) -> Unit)
    : RecyclerView.ViewHolder(habitCardBinding.root) {

    fun bind(habit: Habit){

        val context = itemView.context

        habitCardBinding.apply {

            habitNameTv.text = habit.name
            habitDecriptionTv.text = habit.decr
            habitTypeTv.text = when(habit.type.value){
                Habit.Type.USEFULL.value -> context.getString(R.string.usefull_habit)
                else -> {context.getString(R.string.harmfull_habit)}
            }

            itemView.setOnClickListener { onHabitCardClicked(adapterPosition) }
            habitDoneBtn.setOnClickListener{ onHabitDoneBtnClicked(adapterPosition) }

            habitPriorityTv.text = habit.priority.toString()

            val stringFormat = context.getString(R.string.habit_freq_string_format)

            with(habit.freq){
                habitFreqTv.text = String.format(stringFormat, executionNumber.toString(), countTimePeriod.toString(), timePeriod.time)
            }
        }
    }
}
