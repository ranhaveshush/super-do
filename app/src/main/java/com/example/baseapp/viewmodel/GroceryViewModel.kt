package com.example.baseapp.viewmodel

import androidx.lifecycle.*
import com.example.baseapp.repository.GroceriesRepository
import com.example.baseapp.vo.Grocery
import com.example.baseapp.vo.Resource
import kotlinx.coroutines.Dispatchers

class GroceryViewModel(private val repository: GroceriesRepository) : ViewModel() {
    private val groceriesList: MutableList<Grocery> = ArrayList()

    val groceries: LiveData<Resource<MutableList<Grocery>>> = listGroceries().map { resource ->
        when (resource.state.status) {
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
    }

    private fun listGroceries(): LiveData<Resource<Grocery>> =
        repository.listGroceries().asLiveData(Dispatchers.IO)
}
