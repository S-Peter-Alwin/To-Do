package com.peter.todo

data class ToDoResponse(
    val limit: Int,
    val skip: Int,
    val todos: List<ToDoEntity>,
    val total: Int
)