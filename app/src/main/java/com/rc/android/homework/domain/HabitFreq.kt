package com.rc.android.homework.domain

enum class HabitTimePeriod (val time: String){
    MINUTE("минуту"), HOUR("час"), DAY("день"), WEEK("недель"), MONTH("месяц"), YEAR("год")
}

data class HabitFreq (var executionNumber: Int, var countTimePeriod: Int, var timePeriod: HabitTimePeriod) {

}