package com.example.zonalibros.dataBase

import com.example.zonalibros.dao.AdminDao
import com.example.zonalibros.models.AdminModel
import kotlinx.coroutines.flow.Flow


class AdminRepository (private val adminDao: AdminDao){

    suspend fun insertAdmin(admin: AdminModel): Long {
        return adminDao.insertAdmin(admin)
    }

    fun obtenerAdmins(): Flow<List<AdminModel>>{
        return adminDao.obtenerAdmins()
    }

    suspend fun updateAdmin(admin: AdminModel){
        return adminDao.updateAdmin(admin)
    }

    suspend fun deleteAdmin(admin: AdminModel){
        return adminDao.deleteAdmin(admin)
    }

    suspend fun loginAdmin(usuario: String, clave: String): AdminModel? {
        return adminDao.loginAdmin(usuario, clave)
    }

    suspend fun obtenerPorId(id: Int): AdminModel? {
        return adminDao.obtenerPorId(id)
    }
}