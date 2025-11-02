package com.example.zonalibros.views


import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import java.io.File
import java.util.concurrent.Executors


class RegistroScreen(private val navController: NavHostController? = null){

    @Composable
    fun registro() {

        var nombre by remember { mutableStateOf("") }
        var correo by remember { mutableStateOf("") }
        var clave by remember { mutableStateOf("") }

        val context = LocalContext.current
        val lifecycle = LocalLifecycleOwner.current
        var tenemosPermisoCam by remember {
            mutableStateOf(ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
        }

        val lanzarPermiso = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted -> tenemosPermisoCam = granted}

        var camaraAbierta by remember {mutableStateOf(false)}
        var imagenUri by remember {mutableStateOf<Uri?>(null)}

        val ejecutarCam = remember { Executors.newSingleThreadExecutor()}
        val capturaFoto = remember { ImageCapture.Builder().build() }
        val proveedorCam = remember { ProcessCameraProvider.getInstance(context) }



        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top
        ){
            IconButton(
                onClick = { navController?.navigate("login")},
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "volver al login")
            }

            Text(
                text = "Registro de Usuario",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(32.dp).fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("nombre") })

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = correo, onValueChange = { correo = it }, label = { Text("correo") })

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = clave, onValueChange = { clave = it }, label = { Text("clave") })

            Spacer(modifier = Modifier.height(20.dp))

            ////CAMARA///

            if (camaraAbierta && tenemosPermisoCam) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AndroidView(
                        factory = { ctx ->
                            PreviewView(ctx).apply {
                                val cameraProvider = proveedorCam.get()
                                val vistaPrevia = Preview.Builder().build().also {
                                    it.setSurfaceProvider(this.surfaceProvider)
                                }
                                try {
                                    cameraProvider.unbindAll()
                                    cameraProvider.bindToLifecycle(
                                        lifecycle,
                                        CameraSelector.DEFAULT_BACK_CAMERA,
                                        vistaPrevia,
                                        capturaFoto
                                    )
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    Button(
                        onClick = {
                            val archivoFoto =
                                File(context.cacheDir, "foto_${System.currentTimeMillis()}.jpg")
                            val salidaFoto =
                                ImageCapture.OutputFileOptions.Builder(archivoFoto).build()
                            capturaFoto.takePicture(
                                salidaFoto,
                                ejecutarCam,
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                        imagenUri = Uri.fromFile(archivoFoto)
                                        camaraAbierta = false
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        exception.printStackTrace()
                                    }
                                }
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        Text("Tomar foto")
                    }
                }
            } else {
                Button(
                    onClick = {
                        if (tenemosPermisoCam) {
                            camaraAbierta = true
                        } else {
                            lanzarPermiso.launch(Manifest.permission.CAMERA)
                        }
                    }
                ) {
                    Text("Abrir cámara")
                }

                Spacer(modifier = Modifier.height(16.dp))

                imagenUri?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                }
            }
            ////CAMARA///

            Spacer(modifier = Modifier.height(24.dp))


            Button(onClick = { navController?.navigate("login")
            }) {
                Text("Registrarse")
            }
        }
    }
}