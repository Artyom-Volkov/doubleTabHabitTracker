package com.rc.android.homework.ui.fragment.habitListPagerFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitDatabase
import com.rc.android.homework.databinding.HabitCardBinding

class HabitAdapter (
    public val habits: List<Habit>,
    val onClicked: (Int) -> Unit
) : RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val habitCardBinding = HabitCardBinding.inflate(inflater, parent, false)

        val habitViewHolder = HabitViewHolder( habitCardBinding, ::onHabitClicked  )

        return habitViewHolder
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    private fun onHabitClicked(adapterPosition: Int){
        val habit = habits[adapterPosition]

        val databasePosition = HabitDatabase.getPosition(habit)
        onClicked(databasePosition)
    }

}

