package com.example.todolist.db
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ToDoEntity (
    @PrimaryKey(autoGenerate = true) var id:Int?=null,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "importance") val importance : Int,
    @ColumnInfo(name="date") var date: Long = Calendar.getInstance().timeInMillis,
        )