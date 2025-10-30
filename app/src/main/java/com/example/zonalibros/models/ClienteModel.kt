package com.example.zonalibros.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cliente")
data class ClienteModel (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val nombre:String,
    val direccion:String,
    val telefono:String,
    val correo:String,
    val clave:String

)
