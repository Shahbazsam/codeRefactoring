package com.example.coderefactoring.data.model

import java.time.LocalDate

data class NewToDo(
    val title: String,
    val dueDate: LocalDate,
    val priority: Priority
)

