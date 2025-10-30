package com.example.zonalibros.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "producto")
data class ProductoModel (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val precio: String,
    val autor: String,
    val stock: Int


)