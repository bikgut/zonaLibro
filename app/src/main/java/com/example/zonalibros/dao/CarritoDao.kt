package com.example.zonalibros.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zonalibros.models.CarritoModel
import kotlinx.coroutines.flow.Flow


@Dao
interface CarritoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarAlCarrito(item: CarritoModel)

    @Delete
    suspend fun eliminarCarrito(item: CarritoModel)

    @Query("SELECT * FROM carrito")
    fun obtenerCarrito(): Flow<List<CarritoModel>>

    @Query("DELETE FROM carrito")
    suspend fun vaciarCarrito()
}