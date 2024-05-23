package com.rc.android.homework.data.server.serialization

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.rc.android.homework.data.server.capsule.HabitDone
import java.lang.reflect.Type

class HabitDoneSerializer : JsonSerializer<HabitDone> {

    companion object {
        private const val DATE_JSON_KEY = "date"
        private const val UID_JSON_KEY = "habit_uid"
    }

    override fun serialize(habitDone: HabitDone, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {

        val habitJson = JsonObject().apply{
            addProperty(DATE_JSON_KEY, habitDone.date)
            addProperty(UID_JSON_KEY, habitDone.uid)
        }

        return habitJson
    }

}