package com.example.coderefactoring.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coderefactoring.data.model.NewToDo
import com.example.coderefactoring.data.model.Priority
import com.example.coderefactoring.data.model.ToDo
import com.example.coderefactoring.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
open class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _todos = MutableStateFlow<List<ToDo>>(emptyList())
    val todos = _todos.asStateFlow()

    fun getPendingTodos(): List<ToDo> = _todos.value.filter { !it.isCompleted }

    open fun loadTodos() {
        viewModelScope.launch {
            repository.getLocalTodos().collect {
                _todos.value = it
            }
        }
    }

    fun addTodo(newToDo: NewToDo) {
        viewModelScope.launch {
            repository.addTodo(
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

    open fun refreshOrLoad(forceRefresh: Boolean) {
        if (forceRefresh) {
            sync()
        } else {
            loadTodos()
        }
    }
}
