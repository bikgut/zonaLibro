package com.example.zonalibros

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zonalibros.viewModel.ProductoViewModel
import com.example.zonalibros.views.ProductoScreen
import org.junit.Rule
import org.junit.Test

class PruebaProductoScreen {

    @get:Rule
    val composableRule = createComposeRule()

    @Test
    fun probarAgregarProductoApi(){

        val viewModel = ProductoViewModel()
        composableRule.setContent {
            ProductoScreen(viewModel = viewModel).pantallaProducto()
        }

        composableRule.onNodeWithText("Titulo del libro").performTextInput("Testing")
        composableRule.onNodeWithText("Autor").performTextInput("Testing")
        composableRule.onNodeWithText("Precio").performTextClearance()
        composableRule.onNodeWithText("Precio").performTextInput("21021")
        composableRule.onNodeWithText("Stock disponible").performTextClearance()
        composableRule.onNodeWithText("Stock disponible").performTextInput("21021")
        composableRule.onNodeWithText("URL Imagen").performTextInput("Testing")

        composableRule.onNode(hasText("guardar producto") and hasClickAction())
            .assertExists().performClick()

    }


    @Test
    fun probarEliminarProductoApi(){
        val viewModel = ProductoViewModel()
        composableRule.setContent {
            ProductoScreen(viewModel = viewModel).pantallaProducto()
        }

        composableRule.onNodeWithText("Titulo del libro").performTextInput("TestingDelete")
        composableRule.onNodeWithText("Autor").performTextInput("Testing")
        composableRule.onNodeWithText("Precio").performTextClearance()
        composableRule.onNodeWithText("Precio").performTextInput("21021")
        composableRule.onNodeWithText("Stock disponible").performTextClearance()
        composableRule.onNodeWithText("Stock disponible").performTextInput("21021")
        composableRule.onNodeWithText("URL Imagen").performTextInput("21021")
        composableRule.onNode(hasText("guardar producto") and hasClickAction())
            .assertExists().performClick()

        Thread.sleep(1500)

        composableRule.onNodeWithText("TestingDelete").assertExists()
        composableRule.onNodeWithContentDescription("Eliminar TestingDelete").performClick()

        Thread.sleep(1200)
        composableRule.onNodeWithText("TestingDelete").assertDoesNotExist()
    }


    @Test
    fun probarListaProductos(){
        val viewModel = ProductoViewModel()
        composableRule.setContent {
            ProductoScreen(viewModel = viewModel)
        }

        composableRule.onNodeWithText("Harry Potter y la piedra filosofal")
            .assertExists("El producto buscado no se encuentra en la lista")
    }
}