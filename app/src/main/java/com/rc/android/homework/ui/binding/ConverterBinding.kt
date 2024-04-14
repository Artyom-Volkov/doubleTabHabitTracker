package com.rc.android.homework.ui.binding

import androidx.databinding.InverseMethod

object ConverterBinding {

    @InverseMethod("stringToInt")
    @JvmStatic fun intToString( value: Int?): String {

        val newText: String = value?.toString() ?: ""
        return newText
    }

    @JvmStatic fun stringToInt( value: String): Int? {

        return value.toIntOrNull()
    }
}