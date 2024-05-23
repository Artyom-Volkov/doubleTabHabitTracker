package com.rc.android.homework.data.server

import com.rc.android.homework.data.server.capsule.HabitDone
import com.rc.android.homework.data.server.capsule.HabitUID
import com.rc.android.homework.domain.Habit
import retrofit2.Call
import retrofit2.http.*


interface HabitTrackerNetworkService {

    companion object{
        private const val AUTHORIZATION_TOKEN = "ad947bd0-28d2-441b-b3c5-a4b74101111f"
    }

    @Headers("Authorization: $AUTHORIZATION_TOKEN")
    @GET("habit")
    suspend fun getHabitList(): List<Habit>

    @Headers("Authorization: $AUTHORIZATION_TOKEN")
    @PUT("habit")
    suspend fun addHabit(@Body habit: Habit): HabitUID

    @Headers("Authorization: $AUTHORIZATION_TOKEN")
    @DELETE("habit")
    suspend fun deleteHabit(uid: String): Call<String>

    @Headers("Authorization: $AUTHORIZATION_TOKEN")
    @POST("habit_done")
    suspend fun habitWasDone(@Body habitDone: HabitDone): Call<Void>
}