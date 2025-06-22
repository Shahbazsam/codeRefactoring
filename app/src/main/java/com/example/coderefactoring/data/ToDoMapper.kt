package com.example.coderefactoring.data

import com.example.coderefactoring.data.model.ToDo


interface BaseEntity {          // 🔴 Speculative Generality
    fun toMap(): Map<String, Any>
}

object ToDoMapper {

    fun entityToModel(e: ToDo): ToDo = e.copy()

    fun entityToMap(e: ToDo): Map<String, Any> = mapOf(
        "id" to e.id,
        "title" to e.title,
        "isCompleted" to e.isCompleted,
        "date" to e.dueDate,
        "priority" to e.priority
    )
}
