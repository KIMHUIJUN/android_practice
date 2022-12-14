package com.example.todolist.db

import androidx.room.*

@Dao
interface ToDoDao {
    @Query("SELECT *FROM ToDoEntity")
    fun getALl(): List<ToDoEntity>

    @Insert
    fun insertTodo(todo:ToDoEntity)

    @Delete
    fun deleteTodo(todo: ToDoEntity)

    @Update
    fun updateTodo(todo: ToDoEntity)
}