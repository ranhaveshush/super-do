package com.example.baseapp.util

import com.example.baseapp.api.superdo.SupermarketClient
import com.example.baseapp.repository.GroceriesRepository
import com.example.baseapp.viewmodel.GroceryViewModelFactory

object InjectorUtils {

    fun provideGroceriesViewModelFactory(): GroceryViewModelFactory =
        GroceryViewModelFactory(getGroceriesRepository())

    private fun getGroceriesRepository(): GroceriesRepository =
        GroceriesRepository(getSupermarketClient())

    private fun getSupermarketClient(): SupermarketClient =
        SupermarketClient()
}
