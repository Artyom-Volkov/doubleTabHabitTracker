package com.rc.android.homework.ui.fragment.habitListFragment

import androidx.recyclerview.widget.DiffUtil
import com.rc.android.homework.Habit

class HabitDiffCallback(private val oldList: List<Habit>, private val newList: List<Habit>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem == newItem
    }
}