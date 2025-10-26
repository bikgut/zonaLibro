package com.example.zonalibros.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.zonalibros.models.LoginModel

class LoginViewModel: ViewModel() {

    var loginViewModel by mutableStateOf(LoginModel("", ""))

    fun cambioCorreo(nuevoCorreo:String){
        loginViewModel = loginViewModel.copy(correo = nuevoCorreo)
    }

    fun cambioClave(nuevaClave:String){
        loginViewModel = loginViewModel.copy(clave = nuevaClave)
    }

    //ALERTAS

    var verAlerta by mutableStateOf(false)
        private set

    var tituloAlerta by mutableStateOf("")
    private set

    var mensajeAlerta by mutableStateOf("")
    private set

    var textoBtnAlerta by mutableStateOf("")
    private set

    fun descartarAlerta(){
        verAlerta = false
    }
    //ALERTAS

    //NAVEGACION
    var navegaCliente by mutableStateOf(false)
        private set

    fun cambiarNavegarC(){
        navegaCliente = false
    }

    var navegaAdmin by mutableStateOf(false)
        private set

    fun cambiarNavegarA(){
        navegaAdmin = true
    }

    var navegaRegistro by mutableStateOf(false)
        private set

    fun cambiarNavegarR(){
        navegaRegistro = true
    }

    //NAVEGACION//
    /////////////

    //VARIABLES CONFIRMAR
    var verConfirm by mutableStateOf(false)
    private set

    var tituloConfirm by mutableStateOf("")
        private set

    var mensajeConfirm by mutableStateOf("")
        private set

    var textoBtnConfirm by mutableStateOf("")

    var textoBtnCancelar by mutableStateOf("")
        private set

    fun btnAceptarConfirm(){
        verConfirm = false
    }

    fun btnCancelarConfirm(){
        verConfirm = false
    }

    fun terminarConfirm(){
        verConfirm = false
    }
    //VARIABLES CONFIRMAR

    //VALIDACIONES

    fun auth(){
        Log.d("Correo", loginViewModel.correo)
        Log.d("Clave", loginViewModel.clave)

        if(loginViewModel.correo == "admin" && loginViewModel.clave == "admin"){
            //navegar vista admin
            navegaAdmin = true

        }else if(loginViewModel.correo == "cliente" && loginViewModel.clave == "cliente"){
            navegaCliente = true

        } else if(loginViewModel.correo.isBlank() || loginViewModel.clave.isBlank()){
            tituloAlerta = "Error al iniciar sesion."
            mensajeAlerta = "Correo y clave no pueden estar vacios."
            textoBtnAlerta = "Confirmar"
            verAlerta = true
        }else {

            tituloAlerta = "Error de credenciales."
            mensajeAlerta = "El Correo o la Clave son incorrectos.\n intentelo nuevamente."
            textoBtnAlerta = "Aceptar"
            verAlerta = true
        }
    }

    //VALIDACIONES

}