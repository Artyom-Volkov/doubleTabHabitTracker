package com.rc.android.homework

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "habits")
data class Habit (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val name: String,
    val decr: String,
    val type: @RawValue Type,
    val priority: Int,
    @Embedded val freq: @RawValue HabitFreq,
    @Ignore val color: Int) : Parcelable {

    enum class Type(val value: Int){
        USEFULL(0),
        HARMFULL(1)
    }
}