package com.example.coderefactoring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coderefactoring.ui.ToDoViewModel
import com.example.coderefactoring.ui.theme.CodeRefactoringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeRefactoringTheme {
                val abc = viewModel<ToDoViewModel>()
                val listOfStuff by abc.todos.collectAsState()

                Column {
                    listOfStuff.filter { it.done == false }.forEach {
                        Text(text = it.dataString)
                    }

                    Button(onClick = {
                        abc.addTodo("Wash dishes", "2025-06-01T12:00", 1)
                    }) {
                        Text("Add")
                    }

                    Button(onClick = { abc.refreshOrLoad(true, true) }) {
                        Text("Sync")
                    }
                }
            }
        }
    }
}
