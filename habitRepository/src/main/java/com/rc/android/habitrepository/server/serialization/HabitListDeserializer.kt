package com.rc.android.habitrepository.server.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.rc.android.habitrepository.server.capsule.HabitCapsule
import java.lang.reflect.Type

class HabitListDeserializer: JsonDeserializer<ArrayList<HabitCapsule>> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): ArrayList<HabitCapsule> {

        val resultList = ArrayList<HabitCapsule>()

        val habitSerializer = HabitSerializer()
        if (json.isJsonArray){
            for (jsonItem in json.asJsonArray){
                val habit = habitSerializer.deserialize(jsonItem, typeOfT, context)
                resultList.add(habit)
            }
        }

        return resultList
    }
}