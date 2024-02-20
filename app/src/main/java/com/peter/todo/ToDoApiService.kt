package com.peter.todo

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ToDoApiService {
    @GET("todos")
    suspend fun getToDoList(@Query ("limit") limit:Int,@Query ("skip") skip:Int): ToDoResponse

    @POST("todos/add")
    @Headers("Content-Type: application/json")
    suspend fun addTodo(@Body todo : ToDoEntity): Response<ToDoEntity>

    @PUT("todos/{id}")
    @Headers("Content-Type: application/json")
    suspend fun updateToDo(@Path ("id") id: Long ,@Body todo : ToDoEntity): Response<ToDoEntity>

    @DELETE("todos/{id}")
    @Headers("Content-Type: application/json")
    suspend fun deleteTodo(@Path ("id") id: Long): Response<ToDoEntity>
}