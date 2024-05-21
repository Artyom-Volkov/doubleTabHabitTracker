package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rc.android.homework.domain.HabitTracker

class HabitListsViewModelFactory(private val habitTracker: HabitTracker) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val result: T = modelClass.cast(HabitListsViewModel(habitTracker))
            ?: throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        return result
    }

}