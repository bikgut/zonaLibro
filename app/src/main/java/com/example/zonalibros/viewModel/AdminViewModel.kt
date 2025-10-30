package com.example.zonalibros.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zonalibros.dataBase.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AdminViewModel(private val repository: AdminRepository): ViewModel() {

    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo

    private val _clave = MutableStateFlow("")
    val clave: StateFlow<String> = _clave

    val adminList = repository.obtenerAdmins()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun cambiarId(newId: String) {
        _id.value = newId
    }
    fun cambiarNombre(newNombre: String) {
        _nombre.value = newNombre
    }
    fun cambiarCorreo(newCorreo: String){
        _correo.value = newCorreo
    }
    fun cambiarClave(newClave: String) {
        _clave.value = newClave
    }

    fun guardarAdmin(){
        val
    }

}