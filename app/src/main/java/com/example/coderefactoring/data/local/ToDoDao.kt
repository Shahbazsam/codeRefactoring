package com.example.coderefactoring.data.local

import androidx.room.*
import com.example.coderefactoring.data.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<ToDo>>

    @Insert
    suspend fun insert(todo: ToDo)

    @Delete
    suspend fun delete(todo: ToDo)
}