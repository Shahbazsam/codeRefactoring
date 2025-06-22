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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<ToDo>)

    @Delete
    suspend fun delete(todo: ToDo)

    @Update
    fun update(todo: ToDo)
}
