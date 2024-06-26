package com.rc.android.homework.ui.fragment.habitListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rc.android.habittracker.Habit
import com.rc.android.homework.databinding.HabitCardBinding


class HabitAdapter (
    val onHabitEditingClicked: (Int) -> Unit,
    val onHabitDoneClicked: (Int) -> Unit
) : RecyclerView.Adapter<HabitViewHolder>() {

    private var habits: List<Habit> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val habitCardBinding = HabitCardBinding.inflate(inflater, parent, false)

        val habitViewHolder = HabitViewHolder( habitCardBinding, ::onHabitCardClicked, ::onHabitDoneBtnClicked  )

        return habitViewHolder
    }

    fun setHabitList(newHabits: List<Habit>){
        val diffCallback = HabitDiffCallback(habits, newHabits)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.habits = newHabits
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    private fun onHabitCardClicked(adapterPosition: Int){
        val habit = habits[adapterPosition]

        onHabitEditingClicked(habit.id)
    }

    private fun onHabitDoneBtnClicked(adapterPosition: Int){
        val habit = habits[adapterPosition]

        onHabitDoneClicked(habit.id)
    }

}

