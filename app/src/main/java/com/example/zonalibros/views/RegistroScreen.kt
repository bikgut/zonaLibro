package com.example.zonalibros.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


class RegistroScreen(private val navController: NavHostController? = null){

    @Composable
    fun registro() {

        var nombre by remember { mutableStateOf("") }
        var correo by remember { mutableStateOf("") }
        var clave by remember { mutableStateOf("") }


        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Registrarse",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(32.dp).fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("nombre") })

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = correo, onValueChange = { correo = it }, label = { Text("correo") })

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = clave, onValueChange = { clave = it }, label = { Text("clave") })

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController?.popBackStack()
            }) {
                Text("Registrarse")
            }
        }


        /////////////////////
        //CAMARAA


    }
}