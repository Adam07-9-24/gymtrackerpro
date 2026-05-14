package com.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gymtrackerpro.data.AppDatabase
import com.gymtrackerpro.data.Usuario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(onRegistroSuccess: () -> Unit, onNavigateToLogin: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val scope = rememberCoroutineScope()

    var nombreCompleto by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateToLogin) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2B579A))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre Completo
            FieldWithLabel("Nombre completo", nombreCompleto, "Juan Pérez Vela") { nombreCompleto = it }
            
            Spacer(modifier = Modifier.height(16.dp))

            // Usuario
            FieldWithLabel("Usuario", usuario, "jperez") { usuario = it }

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            FieldWithLabel("Email", email, "juan@mail.com") { email = it }

            Spacer(modifier = Modifier.height(16.dp))

            // Edad
            FieldWithLabel("Edad", edad, "25") { edad = it }

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            FieldWithLabel("Contraseña", password, "********", isPassword = true) { password = it }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (nombreCompleto.isBlank() || usuario.isBlank() || email.isBlank() || edad.isBlank() || password.isBlank()) {
                        errorMessage = "Todos los campos son obligatorios"
                        return@Button
                    }
                    val edadInt = edad.toIntOrNull()
                    if (edadInt == null) {
                        errorMessage = "La edad debe ser un número"
                        return@Button
                    }
                    
                    scope.launch {
                        val existingUser = db.usuarioDao().buscarPorNombreUsuario(usuario)
                        if (existingUser != null) {
                            errorMessage = "El usuario ya existe"
                        } else {
                            db.usuarioDao().registrar(
                                Usuario(
                                    nombreCompleto = nombreCompleto,
                                    nombreUsuario = usuario,
                                    email = email,
                                    edad = edadInt,
                                    contrasena = password
                                )
                            )
                            onRegistroSuccess()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B579A))
            ) {
                Text("Registrarme", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun FieldWithLabel(
    label: String,
    value: String,
    placeholder: String,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Medium, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            shape = RoundedCornerShape(12.dp)
        )
    }
}
