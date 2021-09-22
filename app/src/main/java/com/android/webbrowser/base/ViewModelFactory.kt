package com.android.webbrowser.base

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.webbrowser.database.AppDatabase
import com.android.webbrowser.viewModel.HistoryViewModel
// private val database: AppDatabase
class ViewModelFactory(private val context: Context,private val database: AppDatabase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(HistoryViewModel::class.java) -> HistoryViewModel(context,database)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}