package com.rc.android.homework

enum class HabitTimePeriod (val time: String){
    MINUTE("минуту"), HOUR("час"), DAY("день"), WEEK("недель"), MONTH("месяц"), YEAR("год")
}

data class HabitFreq (var executionNumber: UInt, var countTimePeriod: UInt, var timePeriod: HabitTimePeriod) {

}