package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HabitEditingViewModelFactory(private val position: Int?) : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val result: T = modelClass.cast(HabitEditingViewModel(position))
            ?: throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        return result
    }
}