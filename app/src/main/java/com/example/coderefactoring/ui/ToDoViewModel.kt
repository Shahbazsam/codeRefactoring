package com.example.coderefactoring.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coderefactoring.data.model.ToDo
import com.example.coderefactoring.repository.ToDoRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _todos = MutableStateFlow<List<ToDo>>(emptyList())
    val todos = _todos.asStateFlow()

    open fun refreshOrLoad(forceRefresh: Boolean) {
        if (!forceRefresh) {
            loadTodos()
            return
        }

        sync()
    }

    open fun loadTodos() {
        viewModelScope.launch {
            repository.getLocalTodos().collect {
                _todos.value = it
            }
        }
    }

    open fun addTodo(
        title : String,
        dueDate: String,
        priority: Int
    ) {
        viewModelScope.launch {
            repository.addTodo(
                ToDo(
                    title = title,
                    dueDate = dueDate,
                    priority = priority
                )
            )
        }
    }

    open fun sync() {
        viewModelScope.launch {
            repository.syncTodos(true)
        }
    }
}
