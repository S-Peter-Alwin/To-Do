package com.peter.todo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider

class AddUpdateRepository(private val taskApi: ToDoApiService,private val toDoDao: TodoDao,private val context: Context) {

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

    suspend fun deleteToDo(toDo: ToDoEntity)  : Result<ToDoEntity?> {
        return try {
            if (isOnline(context)) {
                val response = taskApi.deleteTodo(toDo.id!!)
                if (response.isSuccessful) {
                    // API call successful
                    delete(toDo)
                    Result.success(response.body())
                } else {
                    // API call failed
                    delete(toDo)
                    Result.failure(CustomException("Failure"))
                }
            }
            else{
                delete(toDo)
                Result.failure(CustomException("Failure"))
            }
        } catch (e: Exception) {
            delete(toDo)
            // Exception occurred during the API call
            Result.failure(e)
        }
    }
    suspend fun updateToDo(toDo: ToDoEntity)  : Result<ToDoEntity?> {
        return try {
            if (isOnline(context)) {
                val response = taskApi.updateToDo(toDo.id!!,toDo)
                if (response.isSuccessful) {
                    // API call successful
                    update(toDo)
                    Result.success(response.body())
                } else {
                    // API call failed
                    update(toDo)
                    Result.failure(CustomException("Failure"))

                }
            }
            else{
                update(toDo)
                Result.failure(CustomException("Failure"))
            }
        } catch (e: Exception) {
            update(toDo)
            // Exception occurred during the API call
            Result.failure(e)
        }
    }
    suspend fun addToDo(toDo: ToDoEntity)
            : Result<ToDoEntity?> {
        return try {
            if (isOnline(context)) {
                val response = taskApi.addTodo(toDo)
                if (response.isSuccessful) {
                    // API call successful
                    insert(toDo)
                    Result.success(response.body())
                } else {
                    // API call failed
                    insert(toDo)
                    Result.failure(CustomException("Failure"))
                }
            }
            else{
                insert(toDo)
                Result.failure(CustomException("Failure"))
            }
        } catch (e: Exception) {
            insert(toDo)
            // Exception occurred during the API call
            Result.failure(e)
        }
    }
    suspend fun insert(toDo: ToDoEntity) {
        toDoDao.insert(toDo)
        Toast.makeText(context,"To-Do Added Succesfully",Toast.LENGTH_SHORT).show()

    }

    suspend fun update(toDo: ToDoEntity) {
        toDoDao.update(toDo)
        Toast.makeText(context,"To-Do Updated Succesfully",Toast.LENGTH_SHORT).show()

    }

    suspend fun delete(toDo: ToDoEntity) {
        toDoDao.delete(toDo)
        Toast.makeText(context,"To-Do Deleted Succesfully",Toast.LENGTH_SHORT).show()

    }

    class CustomException(message: String) : Exception(message)

}