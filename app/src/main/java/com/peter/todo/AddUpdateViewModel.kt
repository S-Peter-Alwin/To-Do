package com.peter.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AddUpdateViewModel  (private val repository: AddUpdateRepository) : ViewModel() {
//    suspend fun deleteToDo(id: Long) {
//        viewModelScope.launch {
//            val todo = repository.deleteToDo(id)
//        }
//
//    }
private val _actionCompleteEvent = MutableLiveData<ToDoEntity>()
    val actionCompleteEvent: LiveData<ToDoEntity> get() = _actionCompleteEvent



     fun insert(toDo: ToDoEntity) = viewModelScope.launch {
        repository.addToDo(toDo)
         _actionCompleteEvent.postValue(toDo)
    }

     fun update(toDo: ToDoEntity) = viewModelScope.launch {
        repository.updateToDo(toDo)
         _actionCompleteEvent.postValue(toDo)
    }

      fun delete(toDo: ToDoEntity) = viewModelScope.launch {
        repository.deleteToDo(toDo)
          _actionCompleteEvent.postValue(toDo)
    }
}