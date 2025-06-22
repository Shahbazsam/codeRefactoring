package com.example.coderefactoring.data.mapper

import com.example.coderefactoring.data.ToDoMapper
import com.example.coderefactoring.data.model.ToDo
import org.junit.Test
import org.junit.Assert.assertEquals

class ToDoMapperTest {

    @Test
    fun `entityToMap should return correct key-value pairs`() {
        val todo = ToDo(id = 1, dataString = "Test", done = false, dueDate = "2025-07-01", priority = 3)

        val result = ToDoMapper.entityToMap(todo)

        assertEquals(1, result["id"])
        assertEquals("Test", result["data"])
        assertEquals(false, result["done"])
        assertEquals("2025-07-01", result["date"])
        assertEquals(3, result["priority"])
    }
}
