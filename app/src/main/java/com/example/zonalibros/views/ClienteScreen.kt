package com.example.zonalibros.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.zonalibros.R
//import com.example.zonalibros.viewModel.CarritoViewModel
import com.example.zonalibros.viewModel.ProductoViewModel


class ClienteScreen(private val navController: NavHostController? = null, private val viewModel: ProductoViewModel) {

    val state = viewModel.state

    @Composable
    fun cliente() {


        LaunchedEffect(Unit) { viewModel.obtenerProductos() }

        val productoViewModel = viewModel<ProductoViewModel>()


        val titulo = productoViewModel.state.titulo
        val autor = productoViewModel.state.autor
        val precio = productoViewModel.state.precio
        val stock = productoViewModel.state.stock
        val imagen = productoViewModel.state.imagenUrl


        //val listaProd by viewModel.listaProds.collectAsState()


        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {
            Text(
                text = "Bienvenido Cliente",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
            )
            Button(
                onClick = { navController?.navigate("login") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesion")

            }
            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text("Catalogo de productos", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.productos) { product ->
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            AsyncImage(
                                model = product.imagenUrl,
                                contentDescription = "Imagen de libros",
                                modifier = Modifier.height(60.dp),
                                error = painterResource(R.drawable.zonalogo)
                            )
                        }

                        Text(text = "ID: ${product.id}", fontWeight = FontWeight.SemiBold)
                        Text(text = "Titulo: ${product.titulo}")
                        Text(text = "Autor: ${product.autor}")
                        Text(text = "Precio: ${product.precio}")
                        Text(text = "Stock ${product.stock}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            //agregar producto al carrito
                            androidx.compose.material.IconButton(
                                onClick = { navController?.navigate("agregarCarrito/" + product.id) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "carrito"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




