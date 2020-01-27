package com.example.baseapp.util

import com.example.baseapp.api.SupermarketService
import com.example.baseapp.api.superdo.SuperDoApi
import com.example.baseapp.api.superdo.SuperDoClient
import com.example.baseapp.repository.GroceriesRepository
import com.example.baseapp.viewmodel.GroceryViewModelFactory

object InjectorUtils {

    fun provideGroceriesViewModelFactory(): GroceryViewModelFactory =
        GroceryViewModelFactory(getGroceriesRepository())

    private fun getGroceriesRepository(): GroceriesRepository =
        GroceriesRepository(getSupermarketService())

    private fun getSupermarketService(): SupermarketService =
        SuperDoClient(SuperDoApi())
}
