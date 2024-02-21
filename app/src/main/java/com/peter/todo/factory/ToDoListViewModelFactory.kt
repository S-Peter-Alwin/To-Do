package com.peter.todo.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peter.todo.repository.ToDoListRepository
import com.peter.todo.viewmodel.ToDoListViewModel

class ToDoListViewModelFactory(private val repository: ToDoListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            return ToDoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

