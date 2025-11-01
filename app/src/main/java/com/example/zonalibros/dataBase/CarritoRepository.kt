package com.example.zonalibros.dataBase

import com.example.zonalibros.dao.CarritoDao
import com.example.zonalibros.models.CarritoModel
import com.example.zonalibros.models.ProductoModel
import kotlinx.coroutines.flow.Flow

class CarritoRepository(private val carritoDao: CarritoDao) {

    private val carrito = mutableListOf<ProductoModel>()

    suspend fun agregar(item: CarritoModel) = carritoDao.agregarAlCarrito(item)

    suspend fun eliminar(item: CarritoModel) = carritoDao.eliminarCarrito(item)

    fun obtenerCarrito(): Flow<List<CarritoModel>>{
        return carritoDao.obtenerCarrito()
    }

    suspend fun vaciar() = carritoDao.vaciarCarrito()
}