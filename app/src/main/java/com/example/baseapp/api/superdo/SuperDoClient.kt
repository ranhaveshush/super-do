package com.example.baseapp.api.superdo

import com.example.baseapp.api.SupermarketService
import com.example.baseapp.vo.Grocery
import com.example.baseapp.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


/**
 * This client implements the [SupermarketService] contract.
 */
class SuperDoClient(private val api: SuperDoApi) : SupermarketService {

    override fun listGroceries(): Flow<Resource<Grocery>> = flow {
        api.listGroceries().collect { resource ->
            when (resource.state.status) {
                Resource.Status.LOADING -> emit(Resource.loading())
                Resource.Status.SUCCESS -> {
                    resource.data?.let { groceryResponse ->
                        val grocery = groceryResponse.toGrocery()
                        emit(Resource.success(grocery))
                    }
                }
                Resource.Status.FAILURE -> emit(
                    Resource.error(
                        resource.state.message!!,
                        resource.state.cause
                    )
                )
            }
        }
    }
}
