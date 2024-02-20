package com.peter.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "todo") val todo: String?,
    @ColumnInfo(name = "userId") val userId: Long?,
    @ColumnInfo(name = "completed") val completed: Boolean
): java.io.Serializable{

}

