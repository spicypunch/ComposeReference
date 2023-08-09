package com.example.composetodolist.domain.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composetodolist.data.repository.TodoRepositoryImpl
import com.example.composetodolist.domain.repository.TodoRepository
import com.example.composetodolist.ui.main.MainViewModel

class TodoAndroidViewModelFactory(
    private val application: Application,
    private val repository: TodoRepository = TodoRepositoryImpl(
        application
    )
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, repository) as T
        }
        return super.create(modelClass)
    }

}