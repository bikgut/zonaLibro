package com.example.zonalibros.views

import com.example.zonalibros.R
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.zonalibros.alertas.mostrarAlerta
import com.example.zonalibros.alertas.mostrarConfirm
import com.example.zonalibros.viewModel.LoginViewModel


class LoginScreen(private val navController : NavHostController? = null){

    @Composable
    fun login(){

        val viewModel = viewModel <LoginViewModel>()
        val correo = viewModel.loginViewModel.correo
        val clave = viewModel.loginViewModel.clave

        val navC = viewModel.navegaCliente
        val navA = viewModel.navegaAdmin
        val navR = viewModel.navegaRegistro

        if(navC == true){
            navController?.navigate("cliente")
            viewModel.cambiarNavegarC()
        }
        if(navA == true){
            navController?.navigate("admin")
            viewModel.cambiarNavegarA()
        }

        if(navR == true){
            navController?.navigate("registro")
            viewModel.cambiarNavegarR()
        }

        if(viewModel.verAlerta == true){
            mostrarAlerta(
                titulo = viewModel.tituloAlerta,
                mensaje = viewModel.mensajeAlerta,
                onDismiss = {viewModel.descartarAlerta()},
                onConfirm = {viewModel.descartarAlerta()},
                textoBtnConfirmar = viewModel.textoBtnAlerta
            )
        }

        if(viewModel.verConfirm == true){
            mostrarConfirm(
                titulo = viewModel.tituloConfirm,
                mensaje = viewModel.mensajeConfirm,
                textoBtnCancelar = viewModel.textoBtnCancelar,
                textoBtnConfirmar = viewModel.textoBtnConfirm,
                eventoCancelar = {viewModel.btnCancelarConfirm()},
                eventoConfirmar = {viewModel.btnAceptarConfirm()},
                eventoTerminarAlerta = {viewModel.terminarConfirm()}
            )
        }

        var transicion = rememberInfiniteTransition()

        val offsetY by transicion.animateFloat(
            initialValue = 0f,
            targetValue = 20f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
        var cambioColor by remember { mutableStateOf(false) }

        val colorCampo by animateColorAsState(
            if(cambioColor) Color.Transparent else Color.Black
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorCampo)
                .padding(32.dp)
                .clickable{cambioColor = !cambioColor},
            verticalArrangement = Arrangement.Center
        )
        {
            Image(
                painter = painterResource(id = R.drawable.zonalogo),
                contentDescription = "logo",
                modifier = Modifier.offset(y= offsetY.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Iniciar Sesion",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(32.dp).fillMaxWidth().offset(y = offsetY.dp),
                textAlign = TextAlign.Center
            )

            TextField(
                value = correo,
                onValueChange = {viewModel.cambioCorreo(it)},
                label = {Text("Correo")},
                modifier = Modifier.fillMaxWidth()
                    .clickable{cambioColor = !cambioColor}
                    .padding(20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = clave,
                onValueChange = {viewModel.cambioClave(it)},
                label = {Text("Clave")},
                modifier = Modifier.fillMaxWidth()
                    .clickable{cambioColor = !cambioColor}
                    .padding(20.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {viewModel.auth()},
                modifier = Modifier.fillMaxWidth()
                    .clickable{cambioColor = !cambioColor}
                    .padding(20.dp),

                )
            {
                Text("Acceder")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {navController?.navigate("registro")}){
                Text("Registrarse")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun verLogin(){
    LoginScreen().login()
}