package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rc.android.habittracker.HabitTracker

class HabitEditingViewModelFactory(
    private val habitTracker: HabitTracker,
    private val habitId: Int?,
    private val onMakeShortToast : (Int) -> Unit  ) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val result: T = modelClass.cast(HabitEditingViewModel(habitTracker, habitId, onMakeShortToast))
            ?: throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        return result
    }
}