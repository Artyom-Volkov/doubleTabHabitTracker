package com.rc.android.habitrepository.server.capsule

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitFreq

@Entity(tableName = "habits")
data class HabitCapsule(
    val name: String,
    val decr: String,
    val type: Habit.Type,
    val priority: Int,
    @Embedded val freq: HabitFreq,
    val color: Int,
    val done_dates: String,
    val server_uid: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0) {

    companion object{
        fun addDoneDate(habitCapsula: HabitCapsule, date: Long): HabitCapsule{
            var new_done_dates = date.toString()

            if ( !habitCapsula.done_dates.isEmpty() )
                new_done_dates = "," + new_done_dates
            new_done_dates = habitCapsula.done_dates + new_done_dates

            return habitCapsula.copy(done_dates = new_done_dates)//.apply { this.id = habitCapsula.id  }
        }
    }

    constructor(habit: Habit): this(habit, "")

    constructor(habit: Habit, server_uid: String): this(
        name = habit.name,
        decr = habit.decr,
        type = habit.type,
        priority = habit.priority,
        freq = habit.freq,
        color = habit.color,
        done_dates = habit.doneDateList.joinToString(","),
        server_uid = server_uid,
        id = habit.id
    )

    fun toHabit(): Habit{

        val doneDateslist: List<Long> =
            if (done_dates.isEmpty()) {
                emptyList<Long>()
            } else {
                done_dates.split(",").map {  it.toLong() }
            }

        return Habit(name, decr, type, priority, freq, color, doneDateslist, id)
    }
}
