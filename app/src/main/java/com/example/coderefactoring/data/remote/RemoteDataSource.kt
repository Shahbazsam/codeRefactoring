package com.example.coderefactoring.data.remote

import com.example.coderefactoring.data.model.ToDo


class RemoteDataSource(private val api: ApiService) {
    suspend fun fetchTodos(): List<ToDo> {
        return api.getRemoteTodos() // no try-catch
    }
}