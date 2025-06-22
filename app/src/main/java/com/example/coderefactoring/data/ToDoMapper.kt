package com.example.coderefactoring.data

import com.example.coderefactoring.data.model.ToDo


object ToDoMapper {

    fun entityToMap(e: ToDo): Map<String, Any> = mapOf(
        "id" to e.id,
        "title" to e.title,
        "isCompleted" to e.isCompleted,
        "date" to e.dueDate,
        "priority" to e.priority
    )
}
