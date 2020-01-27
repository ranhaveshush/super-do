package com.example.baseapp

import com.example.baseapp.api.superdo.SuperDoClient
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SuperDoClientTest {

    @Test
    @InternalCoroutinesApi
    fun testApi() = runBlocking {
        val client = SuperDoClient()

        val count = 10
        val groceries = client.listGroceries().take(count).toList()

        assert(groceries.size == count)
    }
}
