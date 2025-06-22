package com.example.coderefactoring.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title : String = "",

    var isCompleted : Boolean = false ,
    var dueDate: String = "",
    var priority: Int = 0
)