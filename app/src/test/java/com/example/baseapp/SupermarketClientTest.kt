package com.example.baseapp

import com.example.baseapp.api.superdo.SupermarketClient
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SupermarketClientTest {

    @Test
    @InternalCoroutinesApi
    fun testApi() = runBlocking {
        val client = SupermarketClient()

        val count = 10
        val groceries = client.listGroceries().take(count).toList()

        assert(groceries.size == count)
    }
}
