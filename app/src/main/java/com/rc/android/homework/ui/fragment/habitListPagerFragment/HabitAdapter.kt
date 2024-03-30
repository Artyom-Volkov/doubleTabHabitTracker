package com.rc.android.homework.ui.fragment.habitListPagerFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rc.android.homework.Habit
import com.rc.android.homework.databinding.HabitCardBinding

class HabitAdapter (
    private val habits: List<Habit>,
    val onClicked: (Habit.Type, Int) -> Unit
) : RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val habitCardBinding = HabitCardBinding.inflate(inflater, parent, false)

        val habitViewHolder = HabitViewHolder( habitCardBinding, onClicked  )

        return habitViewHolder
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

}

