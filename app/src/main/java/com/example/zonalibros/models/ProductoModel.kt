package com.example.zonalibros.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


data class ProductoModel (
    @field:Json("id")
    val id: Int = 0,

    @field:Json("titulo")
    val titulo: String,

    @field:Json("precio")
    val precio: Int,

    @field:Json("autor")
    val autor: String,

    @field:Json("stock")
    val stock: Int,

    @field:Json("imagenUrl")
    val imagenUrl: String


)

data class ProductoState(
        val productos: List<ProductoModel> = emptyList(),
    val id:Int? = null,
    val titulo:String = "",
    val precio:String = "",
    val autor:String = "",
    val stock:String = "",
    val imagenUrl:String = ""
)

data class ProductoAgregar(
    @field:Json("titulo")
    val titulo:String,

    @field:Json("precio")
    val precio:Int,

    @field:Json("autor")
    val autor:String,

    @field:Json("stock")
    val stock:Int,

    @field:Json("imagenUrl")
    val imagenUrl:String
)