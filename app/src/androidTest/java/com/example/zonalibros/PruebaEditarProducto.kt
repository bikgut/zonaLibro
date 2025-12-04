package com.example.zonalibros

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.example.zonalibros.views.EditarProductoScreen
import org.junit.Rule
import org.junit.Test

class PruebaEditarProducto {

    @get:Rule
    val composableRule = createComposeRule()

    @Test
    fun probarEditarProdApi(){
    composableRule.setContent {
        EditarProductoScreen(productoId = 1).editarProducto()
    }
        composableRule.onNodeWithText("Titulo").performTextInput("Testing")
        composableRule.onNodeWithText("Precio").performTextClearance()
        composableRule.onNodeWithText("Precio").performTextInput("21021")
        composableRule.onNodeWithText("Autor").performTextInput("Testing")
        composableRule.onNodeWithText("Stock").performTextClearance()
        composableRule.onNodeWithText("Stock").performTextInput("21021")
        composableRule.onNodeWithText("Imagen URL").performTextInput("Testing")

        composableRule.onNode(hasText("Editar producto") and hasClickAction())
            .assertExists().performClick()

    }
}