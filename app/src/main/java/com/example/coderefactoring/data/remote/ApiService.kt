package com.example.coderefactoring.data.remote

import com.example.coderefactoring.data.model.ToDo
import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    suspend fun getRemoteTodos(): List<ToDo> // no Result or error handling
}