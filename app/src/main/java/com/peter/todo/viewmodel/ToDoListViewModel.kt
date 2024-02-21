package com.peter.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peter.todo.repository.ToDoListRepository
import com.peter.todo.db.ToDoEntity
import kotlinx.coroutines.launch

class ToDoListViewModel(private val repository: ToDoListRepository) : ViewModel() {
    private var currentCount = 0
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    val allTasks: LiveData<List<ToDoEntity>> = repository.allTodoFromDB

    init {
        fetchToDoList()
    }

    fun fetchToDoList() {
        viewModelScope.launch {
            try {
                _loadingState.value = true
                repository.getTasks(currentCount)

            } finally {
                _loadingState.value = false
            }


        }
    }

    fun loadMore(count : Int){
        currentCount = count
        fetchToDoList()
    }

}
