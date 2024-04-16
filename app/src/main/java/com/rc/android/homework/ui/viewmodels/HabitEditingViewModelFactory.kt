package com.rc.android.homework.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HabitEditingViewModelFactory(
    private val context: Context,
    private val habitId: Int?,
    private val onMakeShortToast : (String) -> Unit  ) : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val result: T = modelClass.cast(HabitEditingViewModel(context, habitId, onMakeShortToast))
            ?: throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        return result
    }
}