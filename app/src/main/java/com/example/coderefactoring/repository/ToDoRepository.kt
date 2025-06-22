package com.example.coderefactoring.repository

import com.example.coderefactoring.data.local.ToDoDao
import com.example.coderefactoring.data.model.ToDo
import com.example.coderefactoring.data.remote.ApiService
import kotlinx.coroutines.flow.Flow


class ToDoRepository(
    private val dao: ToDoDao,
    private val api: ApiService
) {
    fun getLocalTodos(): Flow<List<ToDo>> = dao.getAll()

    suspend fun addTodo(todo: ToDo) = dao.insert(todo)

    suspend fun syncAndRefresh() {
        val remote = api.getRemoteTodos()
        dao.insertAll(remote)
    }
}
