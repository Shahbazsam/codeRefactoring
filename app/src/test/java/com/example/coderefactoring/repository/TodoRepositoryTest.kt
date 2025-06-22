package com.example.coderefactoring.repository

import com.example.coderefactoring.data.local.ToDoDao
import com.example.coderefactoring.data.model.ToDo
import com.example.coderefactoring.data.remote.ApiService
import io.mockk.Runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import io.mockk.mockk
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ToDoRepositoryTest {

    private val dao: ToDoDao = mockk(relaxed = true)
    private val api: ApiService = mockk()
    private lateinit var repository: ToDoRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = ToDoRepository(dao, api)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `syncTodos inserts remote todos when forceUpdate is true`() = runTest {
        // Arrange
        val mockTodos = listOf(ToDo(id = 1, dataString = "Do laundry"))
        coEvery { api.getRemoteTodos() } returns mockTodos

        // Act
        repository.syncTodos(true)
        testScheduler.advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { dao.insert(any()) }
    }

    @Test
    fun `syncTodos updates local todos when forceUpdate is false`() = runTest {
        // Arrange
        val localTodos = listOf(
            ToDo(id = 1, dataString = "Task 1"),
            ToDo(id = 2, dataString = "Task 2")
        )

        coEvery { dao.getAll() } returns flowOf(localTodos)
        coEvery { dao.update(any()) } just Runs

        // Act
        repository.syncTodos(forceUpdate = false)
        testScheduler.advanceUntilIdle()

        // Assert
        coVerify(exactly = localTodos.size) { dao.update(any()) }
    }

}