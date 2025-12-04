package com.example.zonalibros.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.zonalibros.models.ProductoModel
import com.example.zonalibros.viewModel.ProductoViewModel

class EditarProductoScreen(private val navHostController: NavHostController? = null, private val productoId:Int) {


    @Composable
    fun editarProducto(){

        val productoViewModel = viewModel<ProductoViewModel>()

        val idProducto = this.productoId

        val titulo = productoViewModel.state.titulo
        val precio = productoViewModel.state.precio
        val autor = productoViewModel.state.autor
        val stock = productoViewModel.state.stock
        val imagenUrl = productoViewModel.state.imagenUrl

        LaunchedEffect(productoId) {
            productoViewModel.buscarProd(productoId)
        }

        Column (modifier = Modifier.fillMaxSize().padding(top = 45.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Top
        )
        {
            Text(text = "Editar Producto con el ID: " + idProducto, fontSize = 30.sp)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = titulo,
                onValueChange = {productoViewModel.cambiarTitulo(it)},
                label = { Text("Titulo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))


            OutlinedTextField(
                value = precio,
                onValueChange = {productoViewModel.cambiarPrecio(it)},
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))



            OutlinedTextField(
                value = autor,
                onValueChange = {productoViewModel.cambiarAutor(it)},
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))


            OutlinedTextField(
                value = stock,
                onValueChange = {productoViewModel.cambiarStock(it)},
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))


            OutlinedTextField(
                value = imagenUrl,
                onValueChange = {productoViewModel.cambiarImagen(it)},
                label = { Text("Imagen URL") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))


            Button(
                onClick = {
                    val productoActualizado = ProductoModel(
                        id = idProducto,
                        titulo = productoViewModel.state.titulo,
                        precio = productoViewModel.state.precio.toInt(),
                        autor = productoViewModel.state.autor,
                        stock = productoViewModel.state.stock.toInt(),
                        imagenUrl = productoViewModel.state.imagenUrl
                    )
                    productoViewModel.actualizarProd(productoActualizado)},
                modifier = Modifier.fillMaxWidth()
            ) {
               Text("Editar producto")
            }


        }
    }


}