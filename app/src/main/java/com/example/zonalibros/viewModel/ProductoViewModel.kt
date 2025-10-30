package com.example.zonalibros.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.zonalibros.dataBase.ProductoRepository
import com.example.zonalibros.models.ProductoModel
import kotlinx.coroutines.launch

class ProductoViewModel (private val repository: ProductoRepository): ViewModel() {

    val productos = repository.obtenerProds().asLiveData()

    fun insertProd (producto: ProductoModel) {
        viewModelScope.launch {
            repository.insertProd(producto)
        }
    }

    fun actualizarProd(producto: ProductoModel){
        viewModelScope.launch {
            repository.actualizarProd(producto)
        }
    }

    fun borrarProd(producto: ProductoModel){
        viewModelScope.launch {
            repository.borrarProd(producto)
        }
    }

    fun obtenerPorId(id: Int) =
        repository.obtenerPorId(id)

}