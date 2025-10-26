package com.example.zonalibros.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zonalibros.viewModel.LoginViewModel
import com.example.zonalibros.views.LoginScreen


@Composable
fun navegar(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    )
    {
        composable("login"){
            LoginScreen(navController).login()
        }
    }
}