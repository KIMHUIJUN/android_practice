package com.example.ch16_provider.db

import androidx.room.*

@Dao
interface ImageDao {
    @Query("SELECT *FROM ImageEntity")
    fun getALl(): List<ImageEntity>

    @Insert
    fun insertTodo(todo:ImageEntity)

    @Delete
    fun deleteTodo(todo: ImageEntity)

    @Update
    fun updateTodo(todo: ImageEntity)
}