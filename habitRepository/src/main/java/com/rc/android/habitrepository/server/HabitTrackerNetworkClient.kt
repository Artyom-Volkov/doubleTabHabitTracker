package com.rc.android.habitrepository.server

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rc.android.habitrepository.server.capsule.HabitDone
import com.rc.android.habitrepository.server.capsule.HabitUID
import com.rc.android.habitrepository.server.serialization.HabitDoneSerializer
import com.rc.android.habitrepository.server.serialization.HabitListDeserializer
import com.rc.android.habitrepository.server.serialization.HabitSerializer
import com.rc.android.habitrepository.server.serialization.HabitUIDDeserializer
import com.rc.android.habitrepository.server.serialization.NullOnEmptyConverterFactory
import com.rc.android.habittracker.Habit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
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
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()

        val habitItemListType: Type = object : TypeToken<ArrayList<Habit?>?>() {}.type
        val gson = GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitSerializer())
            .registerTypeAdapter(HabitDone::class.java, HabitDoneSerializer())
            .registerTypeAdapter(HabitUID::class.java, HabitUIDDeserializer())
            .registerTypeAdapter(habitItemListType, HabitListDeserializer())
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
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

    suspend fun habitDone(habitDone: HabitDone): Response<Unit> {
        return networkService.habitWasDone(habitDone)
    }
}