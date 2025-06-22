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

    @Test
    fun `refreshOrLoad should call sync when forceRefresh is true`() = runTest {
        // Arrange
        var syncCalled = false
        var loadCalled = false

        val customViewModel = object : ToDoViewModel(repository) {
            override fun sync() {
                syncCalled = true
            }

            override fun loadTodos() {
                loadCalled = true
            }
        }

        // Act
        customViewModel.refreshOrLoad(forceRefresh = true, showToast = false)

        // Assert
        assert(syncCalled) { "sync() should have been called" }
        assert(!loadCalled) { "loadTodos() should NOT have been called" }
    }

    @Test
    fun `refreshOrLoad should call loadTodos when forceRefresh is false`() = runTest {
        // Arrange
        var syncCalled = false
        var loadCalled = false

        val customViewModel = object : ToDoViewModel(repository) {
            override fun sync() {
                syncCalled = true
            }

            override fun loadTodos() {
                loadCalled = true
            }
        }

        // Act
        customViewModel.refreshOrLoad(forceRefresh = false, showToast = false)

        // Assert
        assert(!syncCalled) { "sync() should NOT have been called" }
        assert(loadCalled) { "loadTodos() should have been called" }
    }
}

