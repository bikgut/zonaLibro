package com.example.zonalibros.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "admin")
data class AdminModel (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val clave: String
)
