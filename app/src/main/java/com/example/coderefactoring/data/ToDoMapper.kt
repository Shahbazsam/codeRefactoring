package com.example.coderefactoring.data

import com.example.coderefactoring.data.model.ToDo


interface BaseEntity {          // 🔴 Speculative Generality
    fun toMap(): Map<String, Any>
}

object ToDoMapper {
    fun entityToModel(e: ToDo): ToDo = ToDo(e.id, e.dataString, e.done, e.dueDate, e.priority)
    fun dtoToEntity(m: ToDo): ToDo = ToDo(m.id, m.dataString, m.done, m.dueDate, m.priority)
    fun entityToMap(e: ToDo): Map<String, Any> = mapOf(   // 🔴 Message Chains
        "id" to e.id,
        "data" to e.dataString,
        "done" to e.done,
        "date" to e.dueDate,
        "priority" to e.priority
    )
}