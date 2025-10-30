package com.example.zonalibros.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zonalibros.dao.AdminDao
import com.example.zonalibros.models.AdminModel


@Database(entities = [AdminModel::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun adminDao(): AdminDao
}