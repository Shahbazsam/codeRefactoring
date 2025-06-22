package com.example.coderefactoring.repository

import com.example.coderefactoring.data.local.ToDoDao
import com.example.coderefactoring.data.remote.ApiService


class ToDoRepository(
    private val dao: ToDoDao,
    private val api: ApiService
) {
    suspend fun syncAndRefresh() {
        val remote = api.getRemoteTodos()
        dao.insertAll(remote)
    }
}

