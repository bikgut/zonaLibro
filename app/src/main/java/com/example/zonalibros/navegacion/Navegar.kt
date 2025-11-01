package com.example.zonalibros.navegacion

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zonalibros.dao.ProductoDao
import com.example.zonalibros.dataBase.ProductoRepository
import com.example.zonalibros.dataBase.ProductoViewModelFactory
import com.example.zonalibros.viewModel.ProductoViewModel
import com.example.zonalibros.views.AdminScreen
import com.example.zonalibros.views.ClienteScreen
import com.example.zonalibros.views.LoginScreen
import com.example.zonalibros.views.ProductoScreen
import com.example.zonalibros.views.RegistroScreen


@Composable
fun navegar(viewModel: ProductoViewModel){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "registro"
    )
    {
        composable("login"){
            LoginScreen(navController).login()
        }
        composable("cliente"){
            ClienteScreen(navController, viewModel).cliente()
        }
        composable("admin"){
            AdminScreen(navController).admin()
        }
        composable("registro"){
            RegistroScreen(navController).registro()
        }
        composable("productos"){
            ProductoScreen(navController, viewModel).pantallaProducto()
        }
    }
}