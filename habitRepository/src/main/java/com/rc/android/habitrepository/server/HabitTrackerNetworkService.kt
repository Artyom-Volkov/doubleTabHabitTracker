package com.rc.android.habitrepository.server

import com.rc.android.habitrepository.server.capsule.HabitDone
import com.rc.android.habitrepository.server.capsule.HabitUID
import com.rc.android.habittracker.Habit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT


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