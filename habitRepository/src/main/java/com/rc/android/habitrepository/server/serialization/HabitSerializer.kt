package com.rc.android.habitrepository.server.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitFreq
import com.rc.android.habittracker.HabitTimePeriod
import java.lang.reflect.Type
import java.util.Date

class HabitSerializer : JsonSerializer<Habit>, JsonDeserializer<Habit> {

    companion object {
        private const val COUNT_JSON_KEY = "count"
        private const val DATE_JSON_KEY = "date"
        private const val DESCRIPTION_JSON_KEY = "description"
        private const val FREQUENCY_JSON_KEY = "frequency"
        private const val PRIORITY_JSON_KEY = "priority"
        private const val TITLE_JSON_KEY = "title"
        private const val TYPE_JSON_KEY = "type"
        private const val UID_JSON_KEY = "uid"
    }

    override fun serialize(habit: Habit, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {

        val habitJson = JsonObject().apply {
            addProperty(COUNT_JSON_KEY, habit.freq.executionNumber)
            val currentDate = Date().time
            addProperty(DATE_JSON_KEY, currentDate)
            addProperty(DESCRIPTION_JSON_KEY, habit.decr)

            addProperty(FREQUENCY_JSON_KEY, frequencySerialize(habit.freq))
            addProperty(PRIORITY_JSON_KEY, habit.priority)
            addProperty(TITLE_JSON_KEY, habit.name)
            addProperty(TYPE_JSON_KEY, habit.type.value)

            if (habit.server_uid != "")
                addProperty(UID_JSON_KEY, habit.server_uid)
        }
        return habitJson
    }

    private fun frequencySerialize(habitFreq: HabitFreq): Int = habitFreq.countTimePeriod * 10 + habitFreq.timePeriod.ordinal

    override fun deserialize( json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Habit {

        json?.asJsonObject?.let {
            val habit = Habit(
                name = it.get(TITLE_JSON_KEY).asString,
                decr = it.get(DESCRIPTION_JSON_KEY).asString,
                type = when (it.get(TYPE_JSON_KEY).asInt) {
                    Habit.Type.USEFULL.value -> Habit.Type.USEFULL
                    Habit.Type.HARMFULL.value -> Habit.Type.HARMFULL
                    else -> {
                        throw IllegalArgumentException()
                    }
                },
                priority = it.get(PRIORITY_JSON_KEY).asInt,
                freq = frequencyDeserialize(it),
                -1,
                server_uid = it.get(UID_JSON_KEY).asString
            )

            return habit
        }

        throw Exception("json deserialize has a Exception")
    }

    private fun frequencyDeserialize(jsonObject: JsonObject): HabitFreq = HabitFreq(
        executionNumber = jsonObject.get(COUNT_JSON_KEY).asInt,
        countTimePeriod = jsonObject.get(FREQUENCY_JSON_KEY).asInt / 10,
        timePeriod = HabitTimePeriod.values()[jsonObject.get(FREQUENCY_JSON_KEY).asInt % 10]
    )

}