package com.example.zonalibros.dataBase

import com.example.zonalibros.dao.ProductoDao
import com.example.zonalibros.models.ProductoModel
import kotlinx.coroutines.flow.Flow

class ProductoRepository (private val productoDao: ProductoDao) {

    suspend fun insertProd(producto: ProductoModel) {
        return productoDao.insertProd(producto)
    }

    suspend fun actualizarProd(producto: ProductoModel){
        return productoDao.actualizarProd(producto)
    }

    suspend fun borrarProd(producto: ProductoModel){
        return productoDao.borrarProd(producto)
    }

    fun obtenerProds(): Flow<List<ProductoModel>>{
        return productoDao.obtenerProds()
    }

    fun obtenerPorId(id: Int): ProductoModel? {
        return productoDao.obtenerPorId(id)
    }
}