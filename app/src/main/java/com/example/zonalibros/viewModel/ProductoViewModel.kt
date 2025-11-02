package com.example.zonalibros.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zonalibros.dataBase.ProductoRepository
import com.example.zonalibros.models.ProductoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel (private val repository: ProductoRepository): ViewModel() {

    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _titulo = MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo

    private val _precio = MutableStateFlow("")
    val precio: StateFlow<String> = _precio

    private val _autor = MutableStateFlow("")
    val autor: StateFlow<String> = _autor

    private val _stock = MutableStateFlow("")
    val stock: StateFlow<String> = _stock

    private val _mostrarDialogo = MutableStateFlow(false)
    val mostrarDialogo: StateFlow<Boolean> = _mostrarDialogo

    private val _productoEliminar = MutableStateFlow<ProductoModel?>(null)

    var verAlerta by mutableStateOf(false)
        private set

    var tituloAlerta by mutableStateOf("")
        private set

    var mensajeAlerta by mutableStateOf("")
        private set

    var textoBtnAlerta by mutableStateOf("")
        private set

    fun descartarAlerta(){
        verAlerta = false
    }


    //limpiar campos form
    private fun limpiarCampos(){
        _id.value = ""
        _titulo.value = ""
        _precio.value = ""
        _autor.value = ""
        _stock.value = ""

    }

    //obtener todos
    val listaProds = repository.obtenerProds().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    //actualizar valores
    fun onTituloChange(nuevoTitulo: String) {
        _titulo.value = nuevoTitulo
    }

    fun onPrecioChange(nuevoPrecio: String) {
        _precio.value = nuevoPrecio
    }

    fun onAutorChange(nuevoAutor: String) {
        _autor.value = nuevoAutor
    }

    fun onStockChange(nuevoStock: String) {
        _stock.value = nuevoStock
    }

    //guardar producto
    fun guardarProducto(onError: (String) -> Unit = {}) {
        if(titulo.value.isBlank() || precio.value.isBlank() || autor.value.isBlank() || stock.value.isBlank()){
            tituloAlerta = "Error al ingresar el producto."
            mensajeAlerta = "Todos los campos son obligatorios."
            textoBtnAlerta = "Confirmar"
            verAlerta = true
        }
        val idInt = id.value.toIntOrNull()?: 0
        val stockInt = stock.value.toIntOrNull() ?: 0
        val producto = ProductoModel(
            titulo = titulo.value,
            precio = precio.value,
            autor = autor.value,
            stock = stockInt
        )
        viewModelScope.launch {
            repository.insertProd(producto)
            limpiarCampos()
        }
    }

    fun iniciarEdicion(producto: ProductoModel){
        _id.value = producto.id.toString()
        _titulo.value = producto.titulo
        _autor.value = producto.autor
        _precio.value = producto.precio
        _stock.value = producto.stock.toString()
    }

    fun actualizarProd(){
        val idProducto = id.value.toIntOrNull() ?: 0
        val stockInt = stock.value.toIntOrNull() ?: 0
        val prodActualizado = ProductoModel(
            id = idProducto,
            titulo = titulo.value,
            precio = precio.value,
            autor = autor.value,
            stock = stockInt
        )
        viewModelScope.launch {
            repository.actualizarProd(prodActualizado)
            limpiarCampos()
        }
    }

    fun confirmEliminacion(producto: ProductoModel){
        _productoEliminar.value = producto
        _mostrarDialogo.value = true
    }

    fun cancelarEliminacio(){
        _mostrarDialogo.value = false
        _productoEliminar.value = null
    }

    fun eliminarconfirm(){
        val producto = _productoEliminar.value ?: return
        viewModelScope.launch {
            repository.borrarProd(producto)
            _productoEliminar.value = null
            _mostrarDialogo.value = false
        }
    }

    //eliminar producto
    fun eliminarProd(producto: ProductoModel) {
        viewModelScope.launch {
            repository.borrarProd(producto)
        }
    }

}