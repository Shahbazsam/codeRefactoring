package com.example.coderefactoring.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title : String = "",
    var isCompleted : Boolean = false,
    var dueDate: LocalDate = LocalDate.now(),
    var priority: Priority = Priority.LOW
)

enum class Priority(val level: Int) {
    LOW(1),
    MEDIUM(2),
    HIGH(3)
}
