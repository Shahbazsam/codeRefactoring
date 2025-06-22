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

    suspend fun syncTodos(forceUpdate: Boolean) {
        if (forceUpdate) {
            val remote = api.getRemoteTodos()
            for (r in remote) {
                if (r.id != 0) dao.insert(r)
                else dao.insert(r) // redundant
            }
        } else {
            dao.getAll().collect { list ->
                list.forEach { dao.update(it) } // nonexistent update method: Dead Code risk
            }
        }
    }
}