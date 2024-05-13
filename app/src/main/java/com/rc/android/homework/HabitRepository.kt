package com.rc.android.homework

import android.content.Context
import androidx.lifecycle.LiveData
import com.rc.android.homework.room.HabitDAO
import com.rc.android.homework.room.HabitRoomDatabase
import com.rc.android.homework.server.HabitTrackerNetworkClient
import com.rc.android.homework.server.capsule.HabitUID
import retrofit2.HttpException


class HabitRepository(context: Context) {

    private val networkClient: HabitTrackerNetworkClient = HabitTrackerNetworkClient()
    private val habitDAO: HabitDAO

    val habits: LiveData<List<Habit>>
        get() = habitDAO.getAllHabits()

    init {
        val habiRoomDatabase = HabitRoomDatabase.getInstance(context)
        habitDAO = habiRoomDatabase.getHabitDAO()
    }

    fun getHabit(id: Int): Habit = habitDAO.getHabitById(id)

    suspend fun updateLocalDatabaseFromServer(){

        val serverHabitList = networkClient.getHabitList()

        for (serverHabit in serverHabitList){

            val localHabit: Habit? = habitDAO.getHabitByServerId(serverHabit.server_uid)//null если привычки нет в локальной базе данных

            localHabit?.let {
                serverHabit.id = it.id//присваивание локального id, потому что сервер не хранит локальный id привычки
            }

            //Чтобы стучатся в базу данных только тогда, когда на сервере действительно произошли изменения.
            if (serverHabit != localHabit){
                habitDAO.add(serverHabit)
            }
        }
    }

    suspend fun add(habit: Habit){

        try {
            val habitUID: HabitUID = networkClient.addHabit(habit)

            habitDAO.add(habit.copy(server_uid = habitUID.toString()))
        } catch (e: HttpException){

        }
    }

    suspend fun replace(habit: Habit){

        try {
            networkClient.replaceHabit(habit)

            habitDAO.update(habit)
        } catch (e: HttpException){

        }
    }
}