package com.rc.android.habitrepository.server.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.rc.android.habitrepository.server.capsule.HabitUID
import java.lang.reflect.Type

class HabitUIDDeserializer : JsonDeserializer<HabitUID> {

    companion object{
        private val UID_JSON_KEY = "uid"
    }

    override fun deserialize( json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): HabitUID {

        val uidStr = json?.asJsonObject?.get(UID_JSON_KEY)?.asString

        return uidStr?.let { HabitUID(it) } ?: throw Exception("json deserialize has a Exception")
    }
}