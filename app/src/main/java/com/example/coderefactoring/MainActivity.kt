package com.example.coderefactoring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coderefactoring.data.model.ToDo
import com.example.coderefactoring.ui.ToDoViewModel
import com.example.coderefactoring.ui.theme.CodeRefactoringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeRefactoringTheme {
                val viewModel = viewModel<ToDoViewModel>()
                Column {
                    ToDoListSection(viewModel.getPendingTodos())
                    ActionButtonsSection(viewModel)
                }
            }
        }
    }
}


@Composable
fun ToDoListSection(todos: List<ToDo>) {
    Column {
        todos.forEach { todo ->
            Text(text = todo.title)
        }
    }
}

@Composable
fun ActionButtonsSection(viewModel: ToDoViewModel) {
    Button(onClick = {
        viewModel.addTodo("Wash dishes", "2025-06-01T12:00", 1)
    }) {
        Text("Add")
    }

    Button(onClick = { viewModel.refreshOrLoad(true, ) }) {
        Text("Sync")
    }
}