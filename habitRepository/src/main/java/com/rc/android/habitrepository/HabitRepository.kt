package com.rc.android.habitrepository

import com.rc.android.habitrepository.room.HabitDAO
import com.rc.android.habitrepository.server.HabitTrackerNetworkClient
import com.rc.android.habitrepository.server.capsule.HabitCapsule
import com.rc.android.habitrepository.server.capsule.HabitDone
import com.rc.android.habitrepository.server.capsule.HabitUID
import com.rc.android.habittracker.Habit
import com.rc.android.habittracker.HabitRepositoryI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.util.Date


class HabitRepository(private val networkClient: HabitTrackerNetworkClient,
                      private val habitDAO: HabitDAO): HabitRepositoryI {

    override val habits: Flow<List<Habit>>
        get() = habitDAO.getAllHabits().map { habitCapsules -> habitCapsules.map { it.toHabit() } }

    override fun getHabit(id: Int): Habit = getHabitCapsule(id).toHabit()

    private fun getHabitCapsule(localID: Int) = habitDAO.getHabitById(localID)

    override suspend fun updateLocalDatabaseFromServer(){

        val serverHabitList = networkClient.getHabitList()

        for (serverHabit in serverHabitList){

            val localHabit: HabitCapsule? = habitDAO.getHabitByServerId(serverHabit.server_uid)//null если привычки нет в локальной базе данных

            localHabit?.let {
                serverHabit.id = it.id//присваивание локального id, потому что сервер не хранит локальный id привычки
            }

            //Чтобы стучатся в базу данных только тогда, когда на сервере действительно произошли изменения.
            if (serverHabit != localHabit){
                habitDAO.add(serverHabit)
            }
        }
    }

    private fun getServerUID(localID: Int): String{
        return getHabitCapsule(localID).server_uid
    }

    override suspend fun add(habit: Habit){

        val habitCapsule = HabitCapsule(habit.copy(id = 0))

        try {
            val habitServerUID: HabitUID = networkClient.addHabit(habitCapsule)

            habitDAO.add(habitCapsule.copy(server_uid = habitServerUID.toString()))
        } catch (e: HttpException){

        }
    }

    override suspend fun replace(habit: Habit){

        val serverUid = getServerUID(habit.id)

        val habitCapsule = HabitCapsule(habit, serverUid)

        try {
            networkClient.replaceHabit(habitCapsule)

            habitDAO.update(habitCapsule)
        } catch (e: HttpException){

        }
    }

    override suspend fun habitDone(habitLocalId: Int): Habit {

        val serverUid = getServerUID(habitLocalId)

        val habitDone = HabitDone( date = Date().time, uid = serverUid )

        try {
            networkClient.habitDone(habitDone)

            val oldHabitCapsule: HabitCapsule = getHabitCapsule(habitLocalId)
            val newHabitCapsule: HabitCapsule = HabitCapsule.addDoneDate(oldHabitCapsule, habitDone.date)

            habitDAO.update(newHabitCapsule)

            return newHabitCapsule.toHabit()

        } catch (e: HttpException){

        }
        return throw Exception()
    }
}