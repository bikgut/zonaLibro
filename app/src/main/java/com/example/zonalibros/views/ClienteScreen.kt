package com.example.zonalibros.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zonalibros.viewModel.ProductoViewModel


class ClienteScreen(private val navController: NavHostController? = null, private val viewModel: ProductoViewModel){

    @Composable
    fun cliente(){
        val listaProd by viewModel.listaProds.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ){
            Text (
                text = "Bienvenido Cliente",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
            )
            Button(
                onClick = {navController?.navigate("login") },
                modifier = Modifier.fillMaxWidth()
                ){
                Text("Cerrar sesion")

            }
            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text("Catalogo de productos", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ){
                items(listaProd){product ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        elevation = 4.dp
                    ){
                        Column(modifier = Modifier.padding(12.dp)){
                            Text(text = "Titulo: ${product.titulo}")
                            Text(text = "Autor: ${product.autor}")
                            Text(text = "Precio: ${product.precio}")
                            Text(text = "Stock: ${product.stock}")
                        }
                    }
                }
            }
        }

    }
}