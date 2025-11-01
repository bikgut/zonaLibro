package com.example.zonalibros.dataBase


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zonalibros.dao.ProductoDao
import com.example.zonalibros.models.ProductoModel


@Database(entities = [ProductoModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
}