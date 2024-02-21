package com.peter.todo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.peter.todo.network.ToDoApiService
import com.peter.todo.db.ToDoEntity
import com.peter.todo.db.TodoDao
import com.peter.todo.util.NetworkUtils

class ToDoListRepository(private val taskApi: ToDoApiService, private val toDoDao: TodoDao, private val context: Context) {
    val allTodoFromDB: LiveData<List<ToDoEntity>> = toDoDao.getAllTodos()

    suspend fun getTasks(currentCount :Int) {
        if(NetworkUtils.isOnline(context)){
            val todo = taskApi.getToDoList(15,currentCount).todos
            insertList(todo)
        }
    }





    suspend fun insertList(toDoList: List<ToDoEntity>) {
        toDoDao.insertList(toDoList)
    }



}
