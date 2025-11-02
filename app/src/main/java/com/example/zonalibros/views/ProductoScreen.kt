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
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.zonalibros.alertas.mostrarAlerta
import com.example.zonalibros.dataBase.AppDataBase
import com.example.zonalibros.dataBase.ProductoRepository
import com.example.zonalibros.dataBase.ProductoViewModelFactory
import com.example.zonalibros.models.ProductoModel

import com.example.zonalibros.viewModel.ProductoViewModel

class ProductoScreen (private val navController: NavHostController? = null, private val viewModel: ProductoViewModel) {

    @Composable
    fun pantallaProducto() {

        val titulo by viewModel.titulo.collectAsState()
        val autor by viewModel.autor.collectAsState()
        val precio by viewModel.precio.collectAsState()
        val stock by viewModel.stock.collectAsState()
        val listaProd by viewModel.listaProds.collectAsState()

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
                onValueChange = { viewModel.onTituloChange(it) },
                label = { Text("Titulo del libro") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = autor,
                onValueChange = { viewModel.onAutorChange(it) },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { viewModel.onPrecioChange(it) },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { viewModel.onStockChange(it) },
                label = { Text("Stock disponible") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.guardarProducto() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("guardar producto")
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text("Lista de productos", style = MaterialTheme.typography.h5)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(listaProd) { product ->
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text(text = "ID: ${product.id}", fontWeight = FontWeight.SemiBold)
                        Text(text = "Titulo: ${product.titulo}")
                        Text(text = "Autor: ${product.autor}")
                        Text(text = "Precio: ${product.precio}")
                        Text(text = "Stock ${product.stock}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            IconButton (
                            onClick = {
                                if (viewModel.id.value.isNotEmpty()) {
                                    viewModel.actualizarProd()
                                }else{
                                    viewModel.guardarProducto { mensajeError ->
                                        mensaje.value = mensajeError
                                    }
                                }
                                viewModel.onTituloChange(product.titulo)
                                viewModel.onPrecioChange(product.precio)
                                viewModel.onAutorChange(product.autor)
                                viewModel.onStockChange(product.stock.toString())
                            }
                            ){
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "editar"
                                )
                            }

                            IconButton(
                                onClick = {
                                    prodSelect = product
                                    mostrarAlert =true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "eliminar"
                                )
                            }
                        }


                    }
                }
            }
            if(mostrarAlert && prodSelect != null){
                AlertDialog(
                    onDismissRequest = {mostrarAlert = false},
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.eliminarProd(prodSelect!!)
                            mostrarAlert = false
                        }) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {mostrarAlert = false }) {
                            Text("Cancelar")
                        }
                    },
                    title = {Text("Confirmar eliminacion")},
                    text = {Text("seguro que quieres eliminar este producto?")}
                )
            }
        }
    }
}

