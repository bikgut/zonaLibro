package com.example.zonalibros.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.room.util.copy
import coil.compose.AsyncImage
import com.example.zonalibros.R
import com.example.zonalibros.alertas.mostrarAlerta
//import com.example.zonalibros.dataBase.AppDataBase
//import com.example.zonalibros.dataBase.ProductoRepository
//import com.example.zonalibros.dataBase.ProductoViewModelFactory
import com.example.zonalibros.models.ProductoModel

import com.example.zonalibros.viewModel.ProductoViewModel

class ProductoScreen (private val navController: NavHostController? = null, private val viewModel: ProductoViewModel) {

    val state = viewModel.state

    @Composable
    fun pantallaProducto() {

        LaunchedEffect(Unit) { viewModel.obtenerProductos() }
        //LaunchedEffect(productoId) { viewModel. }

        val productoViewModel = viewModel<ProductoViewModel>()


        val titulo = productoViewModel.state.titulo
        val autor = productoViewModel.state.autor
        val precio = productoViewModel.state.precio
        val stock = productoViewModel.state.stock
        val imagen = productoViewModel.state.imagenUrl


        /*
        val titulo by viewModel.titulo.collectAsState()
        val autor by viewModel.autor.collectAsState()
        val precio by viewModel.precio.collectAsState()
        val stock by viewModel.stock.collectAsState()
        val listaProd by viewModel.listaProds.collectAsState()

         */

        val mensaje = remember {mutableStateOf("")}

        var mostrarAlert by remember { mutableStateOf(false) }
        var prodSelect by remember {mutableStateOf<ProductoModel?>(null)}

        val haptic = LocalHapticFeedback.current

        if(viewModel.verAlerta == true){
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            mostrarAlerta(
                titulo = viewModel.tituloAlerta,
                mensaje = viewModel.mensajeAlerta,
                onDismiss = {viewModel.descartarAlerta()},
                onConfirm = {viewModel.descartarAlerta()},
                textoBtnConfirmar = viewModel.textoBtnAlerta
            )
        }

        Column(modifier = Modifier.padding(20.dp).systemBarsPadding()) {

            IconButton(
                onClick = { navController?.navigate("admin") }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "volver a vista admin"
                )
            }


            Text(
                text = "Gestionar productos",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = titulo,
                onValueChange = { productoViewModel.cambiarTitulo(it) },
                label = { Text("Titulo del libro") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = autor,
                onValueChange = { productoViewModel.cambiarAutor(it) },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { productoViewModel.cambiarPrecio(it) },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { productoViewModel.cambiarStock(it) },
                label = { Text("Stock disponible") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imagen,
                onValueChange = { productoViewModel.cambiarImagen(it) },
                label = { Text("URL Imagen") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { productoViewModel.guardarProducto()
                          productoViewModel.obtenerProductos()},
                modifier = Modifier.fillMaxWidth(),

            ) {
                Text( "guardar producto")
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text("Lista de productos", style = MaterialTheme.typography.h5)

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

                            //editar producto
                            IconButton(
                                onClick = {navController?.navigate("editarProducto/" + product.id)}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "editar"
                                )
                            }
                            //editar prod

                            //eliminar producto
                            IconButton(
                                onClick = {
                                    prodSelect = product
                                    mostrarAlert = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "eliminar"
                                )
                            }
                            //eliminar prod
                        }


                    }
                }
            }
            if (mostrarAlert && prodSelect != null) {
                prodSelect?.let { producto ->
                    AlertDialog(
                        onDismissRequest = { mostrarAlert = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.eliminarProd(producto.id)
                                    mostrarAlert = false
                                }
                            ) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { mostrarAlert = false }) {
                                Text("Cancelar")
                            }
                        },
                        title = { Text("Confirmar eliminacion") },
                        text = { Text("seguro que quieres eliminar este producto?") }
                    )
                }
            }
        }
    }
}

