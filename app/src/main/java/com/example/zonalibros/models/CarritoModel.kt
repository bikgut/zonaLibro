package com.example.zonalibros.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "carrito")
data class CarritoModel (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val titulo: String,
    val precio: String,
    val cantidad: Int = 1
)