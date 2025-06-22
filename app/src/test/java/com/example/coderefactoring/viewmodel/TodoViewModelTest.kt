package com.example.coderefactoring.viewmodel

import com.example.coderefactoring.repository.ToDoRepository
import com.example.coderefactoring.ui.ToDoViewModel
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ToDoViewModelTest {

    private val repository: ToDoRepository = mockk(relaxed = true)
    private lateinit var viewModel: ToDoViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ToDoViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addTodo should call repository with correct ToDo object`() = runTest {
        // Arrange
        val dataString = "Read book"
        val dueDate = "2025-07-01"
        val priority = 2

        // Act
        viewModel.addTodo(dataString, dueDate, priority)
        testScheduler.advanceUntilIdle()

        // Assert
        coVerify {
            repository.addTodo(
                match {
                    it.dataString == dataString &&
                            it.dueDate == dueDate &&
                            it.priority == priority
                }
            )
        }
    }
}
