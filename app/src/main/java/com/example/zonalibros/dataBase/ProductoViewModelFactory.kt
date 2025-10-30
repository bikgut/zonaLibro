package com.example.zonalibros.dataBase

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.zonalibros.viewModel.ProductoViewModel

class ProductoViewModelFactory (private val repository: ProductoRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}