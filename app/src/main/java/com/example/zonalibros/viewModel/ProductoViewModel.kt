package com.example.zonalibros.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.zonalibros.dataBase.ProductoRepository
import com.example.zonalibros.models.ProductoAgregar
import com.example.zonalibros.models.ProductoModel
import com.example.zonalibros.models.ProductoState
import com.example.zonalibros.repository.ProductoService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel: ViewModel() {

    private val productoService = ProductoService.instance

    var state by mutableStateOf(ProductoState())
        private set

    init {
        obtenerProductos()
    }

    fun cambiarTitulo(nuevoTitulo: String){ state = state.copy(titulo = nuevoTitulo) }

    fun cambiarPrecio(nuevoPrecio: String){ state = state.copy(precio = nuevoPrecio) }

    fun cambiarAutor(nuevoAutor: String){ state = state.copy(autor = nuevoAutor) }

    fun cambiarStock(nuevoStock: String){ state = state.copy(stock = nuevoStock) }

    fun cambiarImagen(nuevaImagen: String){ state = state.copy(imagenUrl = nuevaImagen) }

    fun cambiarId(nuevoId: Int){ state = state.copy(id = nuevoId) }



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

    private val _imagen = MutableStateFlow("")
    val imagen: StateFlow<String> = _imagen

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
        state = state.copy(
            id = null,
            titulo = "",
            precio = "",
            autor = "",
            stock = "",
            imagenUrl = ""
        )
    }

    //obtener todos
    // val listaProds = repository.obtenerProds().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun obtenerProductos(){
        viewModelScope.launch {
            try {
                val listaProd = productoService.listarProductos()
                state = state.copy(productos = listaProd)

            }catch (e: Exception){

            }
        }
    }


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

    fun onImagenChange(nuevoImagen: String){
        _imagen.value = nuevoImagen
    }

    //guardar producto
    fun guardarProducto() {
        if(state.titulo.isBlank() || state.precio.isBlank() || state.autor.isBlank() || state.stock.isBlank() ||state.imagenUrl.isBlank()){
            tituloAlerta = "Error al ingresar el producto."
            mensajeAlerta = "Todos los campos son obligatorios."
            textoBtnAlerta = "Confirmar"
            verAlerta = true
            return
        }
        viewModelScope.launch {
            try {
                val stockInt = state.stock.toIntOrNull() ?: 0

                val nuevoProducto = ProductoAgregar(
                    titulo = state.titulo,
                    precio = state.precio.toInt(),
                    autor = state.autor,
                    stock = state.stock.toInt(),
                    imagenUrl = state.imagenUrl
                )

                productoService.agregarProducto(nuevoProducto)
                obtenerProductos()

                state = state.copy(
                    titulo = "",
                    precio = "",
                    autor = "",
                    stock = "",
                    imagenUrl = ""
                )

            }catch (e: Exception){
                tituloAlerta = "Error"
                mensajeAlerta = "No se pudo conectar con el servidor."
                textoBtnAlerta = "Aceptar"
                verAlerta = true

            }
        }
    }

    fun iniciarEdicion(producto: ProductoModel){
        state = state.copy(
            id = producto.id,
            titulo = producto.titulo,
            autor = producto.autor,
            precio= producto.precio.toString(),
            stock = producto.stock.toString(),
            imagenUrl = producto.imagenUrl
        )

    }

    fun buscarProd(productoId: Int){
        viewModelScope.launch {
            try {
                val productoEncontrado = productoService.obtenerPorId(productoId)
                cambiarTitulo(productoEncontrado.titulo)
                cambiarAutor(productoEncontrado.autor)
                cambiarPrecio(productoEncontrado.precio.toString())
                cambiarStock(productoEncontrado.stock.toString())
                cambiarImagen(productoEncontrado.imagenUrl)
                cambiarId(productoEncontrado.id)
            }catch (e: Exception){

            }
        }
    }


    fun actualizarProd(producto: ProductoModel) {

        state = state.copy(
            titulo = producto.titulo ,
            precio = producto.precio.toString(),
            autor = producto.autor,
            stock = producto.stock.toString(),
            imagenUrl = producto.imagenUrl
        )
        viewModelScope.launch {
            try {
                productoService.actualizarProducto(producto)
            }catch (e: Exception){

            }

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
            productoService.eliminarProducto(producto.id)
            _productoEliminar.value = null
            _mostrarDialogo.value = false
        }
    }

    //eliminar producto
    fun eliminarProd(productoId : Int) {
        viewModelScope.launch {
            try {
                if(productoId != null){
                    productoService.eliminarProducto(productoId)
                }
                obtenerProductos()
            }catch (e: Exception){

            }
        }
    }

}