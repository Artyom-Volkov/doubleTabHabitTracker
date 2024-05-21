package com.rc.android.homework.data.server

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rc.android.homework.data.server.capsule.HabitUID
import com.rc.android.homework.data.server.serialization.HabitListDeserializer
import com.rc.android.homework.data.server.serialization.HabitSerializer
import com.rc.android.homework.data.server.serialization.HabitUIDDeserializer
import com.rc.android.homework.domain.Habit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class HabitTrackerNetworkClient {

    companion object {
        private const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"
    }

    private val networkService: HabitTrackerNetworkService

    init {
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()

        val habitItemListType: Type = object : TypeToken<ArrayList<Habit?>?>() {}.type
        val gson = GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitSerializer())
            .registerTypeAdapter(HabitUID::class.java, HabitUIDDeserializer())
            .registerTypeAdapter(habitItemListType, HabitListDeserializer())
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        networkService = retrofit.create(HabitTrackerNetworkService::class.java)
    }

    suspend fun getHabitList(): List<Habit>{
        return networkService.getHabitList()
    }

    suspend fun addHabit(habit: Habit): HabitUID {
        return networkService.addHabit(habit)
    }

    suspend fun replaceHabit(habit: Habit): HabitUID {
        return networkService.addHabit(habit)
    }
}