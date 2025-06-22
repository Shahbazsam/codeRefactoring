package com.example.coderefactoring.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coderefactoring.data.local.ToDoDao
import com.example.coderefactoring.data.model.NewToDo
import com.example.coderefactoring.data.model.ToDo
import com.example.coderefactoring.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ToDoViewModel @Inject constructor(
    private val dao: ToDoDao,
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

    fun getPendingTodos(): List<ToDo> = _todos.value.filter { !it.isCompleted }

    open fun loadTodos() {
        viewModelScope.launch {
            dao.getAll().collect {
                _todos.value = it
            }
        }
    }

    open fun addTodo(newToDo: NewToDo) {
        viewModelScope.launch {
            dao.insert(
                ToDo(
                    title = newToDo.title,
                    dueDate = newToDo.dueDate,
                    priority = newToDo.priority
                )
            )
        }
    }

    open fun sync() {
        viewModelScope.launch {
            repository.syncAndRefresh()
        }
    }
}

