package com.peter.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ToDo(
    val completed: Boolean,
    val id: Long,
    val todo: String,
    val userId: Long
){
    fun toTodoEntity(): ToDoEntity {
        // Use the additional value as needed during conversion
        // For example, you might set some property in TodoEntity based on this value
        return ToDoEntity(id, todo, userId, completed)
    }

}