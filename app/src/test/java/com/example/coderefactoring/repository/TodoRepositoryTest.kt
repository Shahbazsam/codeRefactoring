package com.example.coderefactoring.repository

import com.example.coderefactoring.data.local.ToDoDao
import com.example.coderefactoring.data.model.ToDo
import com.example.coderefactoring.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import io.mockk.mockk
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
}