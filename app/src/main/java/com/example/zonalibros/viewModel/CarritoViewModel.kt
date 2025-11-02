package com.example.zonalibros.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zonalibros.dataBase.CarritoRepository
import com.example.zonalibros.models.CarritoModel
import com.example.zonalibros.models.ProductoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {

    val listaCarrito = repository.obtenerCarrito().stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    private fun calcularTotal(){
        viewModelScope.launch {
            listaCarrito.value.let { lista -> _total.value = lista.sumOf { it.precio.toDouble() * it.cantidad }}
        }
    }

    fun agregarProd(producto: ProductoModel){
        val item = CarritoModel(
            productoId = producto.id,
            titulo = producto.titulo,
            precio = producto.precio,
            cantidad = 1
        )
        viewModelScope.launch {
            repository.agregar(item)
            calcularTotal()
        }
    }

    fun eliminarDelCarrito(item: CarritoModel){
        viewModelScope.launch {
            repository.eliminar(item)
            calcularTotal()
        }
    }

    fun vaciarCarrito(){
        viewModelScope.launch {
            repository.vaciar()
            _total.value = 0.0
        }

    }

}