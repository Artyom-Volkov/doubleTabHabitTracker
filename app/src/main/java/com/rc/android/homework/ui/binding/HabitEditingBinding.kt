package com.rc.android.homework.ui.binding

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object HabitEditingBinding {

    @BindingAdapter(value = ["app:habitExecutionNumber", "custom:AttrChanged"], requireAll = false)
    fun EditText.updateText(execution: Int?, listener: InverseBindingListener) {

        val executionStr = execution.toString()

        if (this.text.toString() != executionStr) {
            this.setText(executionStr)
        }
        this.doOnTextChanged { _: CharSequence?, _: Int?, _: Int?, _: Int? ->
            listener.onChange()
        }
    }

    @InverseBindingAdapter(attribute = "app:habitExecutionNumber", event = "custom:AttrChanged")
    fun EditText.getUpdatedText(): Int? {
        return this.text.toString().toInt()
    }
}