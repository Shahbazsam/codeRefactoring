package com.example.coderefactoring.data.remote

import org.junit.Test

class RemoteDataSourceHelperTest {

    @Test
    fun `printStatus should run without exception`() {
        val helper = RemoteDataSourceHelper()
        helper.printStatus()
        // No assertion needed; just confirming it exists and does not crash
    }
}
