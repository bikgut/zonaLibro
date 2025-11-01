package com.example.zonalibros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.zonalibros.dataBase.AppDataBase
import com.example.zonalibros.dataBase.ProductoRepository
import com.example.zonalibros.dataBase.ProductoViewModelFactory
import com.example.zonalibros.navegacion.navegar
import com.example.zonalibros.ui.theme.ZonaLibrosTheme
import com.example.zonalibros.viewModel.ProductoViewModel

class MainActivity : ComponentActivity() {

    private val viewModel : ProductoViewModel by viewModels {
        ProductoViewModelFactory(
            ProductoRepository(
                Room.databaseBuilder(
                    applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .productoDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZonaLibrosTheme {
                Surface (
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ){
                    navegar(viewModel)
                }
            }
        }
    }
}