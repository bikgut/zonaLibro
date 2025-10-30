package com.example.zonalibros.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zonalibros.models.ProductoModel
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProd(producto: ProductoModel)

    @Update
    suspend fun actualizarProd(producto: ProductoModel)

    @Delete
    suspend fun borrarProd(producto: ProductoModel)

    @Query("SELECT * FROM producto")
    fun obtenerProds(): Flow<List<ProductoModel>>

    @Query("SELECT * FROM producto WHERE id = :id")
    fun obtenerPorId(id: Int): ProductoModel?

}