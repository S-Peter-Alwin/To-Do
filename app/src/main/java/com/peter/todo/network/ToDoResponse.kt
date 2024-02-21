package com.peter.todo.network

import com.peter.todo.db.ToDoEntity

data class ToDoResponse(
    val limit: Int,
    val skip: Int,
    val todos: List<ToDoEntity>,
    val total: Int
)