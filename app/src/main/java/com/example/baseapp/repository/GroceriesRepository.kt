package com.example.baseapp.repository

import com.example.baseapp.api.SupermarketService
import com.example.baseapp.vo.Grocery
import com.example.baseapp.vo.Resource
import kotlinx.coroutines.flow.Flow

class GroceriesRepository(private val service: SupermarketService) {
    fun listGroceries(): Flow<Resource<Grocery>> = service.listGroceries()
}
