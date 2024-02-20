package com.peter.todo.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peter.todo.viewmodel.AddUpdateViewModel

class AddUpdateViewModelFactory(private val repository: AddUpdateRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddUpdateViewModel::class.java)) {
            return AddUpdateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

