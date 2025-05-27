package com.example.coderefactoring.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coderefactoring.data.model.ToDo

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao
}