package com.peter.todo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: ToDoEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(todo: List<ToDoEntity>)

    @Delete
    suspend fun delete(todo: ToDoEntity) : Int

    @Query("SELECT * from todo_table")
    fun getAllTodos(): LiveData<List<ToDoEntity>>

    @Update
    suspend fun update(todo: ToDoEntity) : Int
}
