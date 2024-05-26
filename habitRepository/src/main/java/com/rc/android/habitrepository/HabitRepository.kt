package com.rc.android.habitrepository

import com.rc.android.habitrepository.room.HabitDAO
import com.rc.android.habitrepository.server.HabitTrackerNetworkClient
import com.rc.android.habitrepository.server.capsule.HabitDone
import com.rc.android.habitrepository.server.capsule.HabitUID
import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitRepositoryI
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.util.Date


class HabitRepository(private val networkClient: HabitTrackerNetworkClient,
                      private val habitDAO: HabitDAO): HabitRepositoryI {

    override val habits: Flow<List<Habit>>
        get() = habitDAO.getAllHabits()

    init {
    }

    override fun getHabit(id: Int): Habit = habitDAO.getHabitById(id)

    override suspend fun updateLocalDatabaseFromServer(){

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

    override suspend fun add(habit: Habit){

        try {
            val habitUID: HabitUID = networkClient.addHabit(habit)

            habitDAO.add(habit.copy(server_uid = habitUID.toString()))
        } catch (e: HttpException){

        }
    }

    override suspend fun replace(habit: Habit){

        try {
            networkClient.replaceHabit(habit)

            habitDAO.update(habit)
        } catch (e: HttpException){

        }
    }

    override suspend fun habitDone(habit: Habit) {

        val habitDone = HabitDone(
            date = Date().time,
            uid = habit.server_uid
        )

        networkClient.habitDone(habitDone)
    }
}