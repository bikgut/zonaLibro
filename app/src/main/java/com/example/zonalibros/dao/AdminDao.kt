package com.example.zonalibros.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zonalibros.models.AdminModel
import kotlinx.coroutines.flow.Flow


@Dao
interface AdminDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdmin(admin: AdminModel): Long

    @Query("SELECT * FROM admin ORDER BY id DESC")
    fun obtenerAdmins(): Flow<List<AdminModel>>

    @Update
    suspend fun updateAdmin(admin: AdminModel)

    @Delete
    suspend fun deleteAdmin(admin: AdminModel)

    //Consultas login
    @Query("SELECT * FROM admin WHERE correo = :usuario AND clave = :clave LIMIT 1")
    suspend fun loginAdmin(usuario: String, clave: String): AdminModel?

    @Query("SELECT * FROM admin WHERE id = :id")
    suspend fun obtenerPorId(id: Int): AdminModel?

}