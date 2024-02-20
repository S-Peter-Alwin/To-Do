package com.peter.todo

import androidx.room.ColumnInfo

data class ToDoApi(@ColumnInfo(name = "todo") val todo: String?,
                   @ColumnInfo(name = "userId") val userId: Long?,
                   @ColumnInfo(name = "completed") val completed: Boolean
): java.io.Serializable
