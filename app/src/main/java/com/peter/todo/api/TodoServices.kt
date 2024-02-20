package com.peter.todo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TodoServices {
    private const val BASE_URL = "https://dummyjson.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val taskApiService: ToDoApiService by lazy {
        retrofit.create(ToDoApiService::class.java)
    }

}