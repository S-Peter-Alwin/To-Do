package com.peter.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddUpdateViewModelFactory(private val repository: AddUpdateRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddUpdateViewModel::class.java)) {
            return AddUpdateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

