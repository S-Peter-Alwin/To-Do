package com.peter.todo.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import com.peter.todo.api.ToDoApiService
import com.peter.todo.db.ToDoEntity
import com.peter.todo.db.TodoDao

class ToDoListRepository(private val taskApi: ToDoApiService, private val toDoDao: TodoDao, private val context: Context) {

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }





    val allTodoFromDB: LiveData<List<ToDoEntity>> = toDoDao.getAllTodos()
    suspend fun getTasks(currentCount :Int) {
        if(isOnline(context)){
            val todo = taskApi.getToDoList(15,currentCount).todos
            insertList(todo)
        }
    }





    suspend fun insertList(toDoList: List<ToDoEntity>) {
        toDoDao.insertList(toDoList)
    }



}
