package com.example.baseapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseapp.repository.GroceriesRepository

class GroceryViewModelFactory(
    private val repository: GroceriesRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GroceryViewModel(repository) as T
    }
}
