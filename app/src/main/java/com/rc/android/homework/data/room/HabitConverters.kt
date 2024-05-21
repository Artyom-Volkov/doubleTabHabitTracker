package com.rc.android.homework.data.room

import androidx.room.TypeConverter
import com.rc.android.homework.domain.Habit
import com.rc.android.homework.domain.HabitTimePeriod

class HabitConverters {

    @TypeConverter
    fun fromHabitType(type: Habit.Type): String{
        return type.name
    }

    @TypeConverter
    fun toHabitType(typeStr: String): Habit.Type{
        return enumValueOf(typeStr)
    }

    @TypeConverter
    fun fromHabitTimePeriod(timePeriod: HabitTimePeriod): String{
        return timePeriod.name
    }

    @TypeConverter
    fun toHabitTimePeriod(timePeriodStr: String): HabitTimePeriod{
        return enumValueOf(timePeriodStr)
    }
}