package com.example.zonalibros.dataBase


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zonalibros.dao.CarritoDao
import com.example.zonalibros.dao.ProductoDao
import com.example.zonalibros.models.CarritoModel
import com.example.zonalibros.models.ProductoModel


@Database(entities = [ProductoModel::class, CarritoModel::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao

    abstract fun carritoDao(): CarritoDao
}