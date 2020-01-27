package com.example.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.baseapp.repository.GroceriesRepository
import com.example.baseapp.vo.Grocery
import com.example.baseapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class GroceryViewModel(private val repository: GroceriesRepository) : ViewModel() {
    private val groceriesList: MutableList<Grocery> = ArrayList()

    val groceries: LiveData<Resource<MutableList<Grocery>>> = liveData(Dispatchers.IO, 0) {
        repository.listGroceries().collect { resource ->
            val newResource = when (resource.state.status) {
                Resource.Status.LOADING -> Resource.loading()
                Resource.Status.SUCCESS -> {
                    groceriesList.add(resource.data!!)
                    Resource.success(groceriesList)
                }
                Resource.Status.FAILURE -> Resource.error(
                    resource.state.message!!,
                    resource.state.cause
                )
            }

            emit(newResource)
        }
    }
}
