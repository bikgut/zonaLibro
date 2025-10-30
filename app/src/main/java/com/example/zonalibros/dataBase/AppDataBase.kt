package com.example.zonalibros.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zonalibros.dao.AdminDao
import com.example.zonalibros.dao.ProductoDao
import com.example.zonalibros.models.AdminModel
import com.example.zonalibros.models.ProductoModel


@Database(entities = [AdminModel::class, ProductoModel::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun adminDao(): AdminDao

    abstract fun productoDao(): ProductoDao
}