package com.example.zonalibros.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


class RegistroScreen(private val navController: NavHostController? = null){

    @Composable
    fun registro(){

        var nombre by remember { mutableStateOf("") }
        var correo by remember {mutableStateOf("")}
        var clave by remember {mutableStateOf("")}

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            TextField(value = nombre, onValueChange = {nombre = it},label = {Text("nombre")})
            TextField(value = correo, onValueChange = {correo = it}, label = {Text("correo")})
            TextField(value = clave, onValueChange = {clave = it}, label = {Text("clave")})
            Button(onClick = {
                navController?.popBackStack()
            }){
                Text("Registrarse")
            }
        }
    }
}