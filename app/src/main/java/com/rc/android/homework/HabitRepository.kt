package com.rc.android.homework

import android.content.Context
import com.rc.android.homework.room.HabitDAO
import com.rc.android.homework.room.HabitRoomDatabase

class HabitRepository(context: Context) {

    interface Listener {
        fun onHabitAdded(habits : List<Habit>)
        fun onHabitReplaced(position: Int, habits : List<Habit>)
    }

    private val habitDAO: HabitDAO

    private var listener: Listener? = null

    private val habitMutableList: MutableList<Habit> = mutableListOf<Habit>()

    val habits: List<Habit>
        get() = habitMutableList.toList()

    init {
        val habiRoomDatabase = HabitRoomDatabase.getInstance(context)
        habitDAO = habiRoomDatabase.getHabitDAO()

        makeTestHabitList()
    }

    private fun makeTestHabitList(){
        for (i in 1..16 step 2){
            val habitFreq = HabitFreq(i, i*2, HabitTimePeriod.HOUR)
            val habit0 = Habit("name$i", "description$i", Habit.Type.USEFULL, 3, habitFreq, -1)
            val habit1 = Habit("name" + (i + 1), "description" + (i + 1), Habit.Type.HARMFULL, 3, habitFreq, -1)
            habitMutableList.apply{
                add(habit0)
                add(habit1)
            }
        }

    }

    fun getHabit(position: Int): Habit = habitMutableList.get(position)

    fun setListener(listener: Listener){
        this.listener = listener
    }

    fun unsetListener(){
        this.listener = null
    }

    fun getPosition(habit: Habit): Int{

        var index = 0
        for (habitFromList: Habit in habitMutableList){
            if (habit === habitFromList)
                return index
            index++
        }

        return -1
    }

    fun add(habit: Habit){
        //habitMutableList.add(habit)

        habitDAO.add(habit)

        listener?.onHabitAdded(habits)
    }

    fun replace(position: Int, habit: Habit){
        //habitMutableList.set(position, habit)

        habitDAO.update(habit)

        listener?.onHabitReplaced(position, habits)
    }
}