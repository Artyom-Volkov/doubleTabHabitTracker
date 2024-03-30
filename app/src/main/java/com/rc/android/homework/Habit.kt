package com.rc.android.homework

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Habit (val name: String,
                  val decr: String,
                  val type: @RawValue Type,
                  val priority: Int,
                  val freq:  @RawValue HabitFreq,
                  val color: Int) : Parcelable {

    enum class Type(val value: Int){
        USEFULL(0),
        HARMFULL(1)
    }
}