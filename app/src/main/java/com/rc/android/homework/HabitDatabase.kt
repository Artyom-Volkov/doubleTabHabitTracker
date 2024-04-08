package com.rc.android.homework

object HabitDatabase {

    interface Listener {
        public fun onHabitAdded()
        public fun onHabitReplaced(position: Int)
    }

    private var listener: Listener? = null

    val habits: MutableList<Habit> = mutableListOf<Habit>()

    init {
        for (i in 1..16 step 2){
            val habitFreq = HabitFreq(i.toUInt(), (i*2).toUInt(), HabitTimePeriod.HOUR)
            val habit0 = Habit("name$i", "description$i", Habit.Type.USEFULL, 3, habitFreq, -1)
            val habit1 = Habit("name" + (i + 1), "description" + (i + 1), Habit.Type.HARMFULL, 3, habitFreq, -1)
            habits.apply{
                add(habit0)
                add(habit1)
            }
        }
    }

    public fun getHabit(position: Int): Habit = habits.get(position)

    fun getFilterList(habitType: Habit.Type) : MutableList<Habit> = habits.filter { it.type == habitType }.toMutableList()

    fun setListener(listener: Listener){
        this.listener = listener
    }

    fun unsetListener(){
        this.listener = null
    }

    public fun getPosition(habit: Habit): Int{

        var index = 0
        for (habitFromList: Habit in habits){
            if (habit === habitFromList)
                return index
            index++
        }

        return -1
    }

    public fun getPosition(habitType: Habit.Type, position: Int): Int{

        val habits = getFilterList(habitType)
        val habit = habits.get(position)
        return getPosition(habit)
    }

    public fun add(habit: Habit){
        habits.add(habit)

        listener?.onHabitAdded()
    }

    public fun replace(position: Int, habit: Habit){
        habits.set(position, habit)

        listener?.onHabitReplaced(position)
    }
}