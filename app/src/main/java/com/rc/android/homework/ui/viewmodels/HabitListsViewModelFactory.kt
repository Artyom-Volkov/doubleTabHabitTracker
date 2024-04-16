package com.rc.android.homework.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HabitListsViewModelFactory(val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val result: T = modelClass.cast(HabitListsViewModel(context))
            ?: throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        return result
    }


}