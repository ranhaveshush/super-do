package com.example.baseapp.api

import com.example.baseapp.vo.Grocery
import com.example.baseapp.vo.Resource
import kotlinx.coroutines.flow.Flow

interface SupermarketService {
    fun listGroceries(): Flow<Resource<Grocery>>
}
