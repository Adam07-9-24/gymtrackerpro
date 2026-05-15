package com.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gymtrackerpro.data.AppDatabase
import com.gymtrackerpro.data.Rutina
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarRutinaScreen(
    usuarioId: Int,
    onRutinaGuardada: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val scope = rememberCoroutineScope()

    val azul = Color(0xFF2B579A)

    var ejercicio by remember { mutableStateOf("") }
    var grupoMuscular by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticiones by remember { mutableStateOf("") }
    var pesoKg by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nueva rutina",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = azul
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = ejercicio,
                onValueChange = { ejercicio = it },
                label = { Text("Ejercicio") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = grupoMuscular,
                onValueChange = { grupoMuscular = it },
                label = { Text("Grupo muscular") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = series,
                onValueChange = { series = it },
                label = { Text("Series") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = repeticiones,
                onValueChange = { repeticiones = it },
                label = { Text("Repeticiones") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pesoKg,
                onValueChange = { pesoKg = it },
                label = { Text("Peso (kg)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (
                        ejercicio.isBlank() ||
                        grupoMuscular.isBlank() ||
                        series.isBlank() ||
                        repeticiones.isBlank() ||
                        pesoKg.isBlank() ||
                        fecha.isBlank()
                    ) {
                        errorMessage = "Todos los campos son obligatorios"
                        return@Button
                    }

                    val seriesInt = series.toIntOrNull()
                    val repeticionesInt = repeticiones.toIntOrNull()
                    val pesoDouble = pesoKg.toDoubleOrNull()

                    if (
                        seriesInt == null ||
                        repeticionesInt == null ||
                        pesoDouble == null
                    ) {
                        errorMessage = "Series, repeticiones y peso deben ser numéricos"
                        return@Button
                    }

                    scope.launch {
                        val nuevaRutina = Rutina(
                            usuarioId = usuarioId,
                            ejercicio = ejercicio,
                            grupoMuscular = grupoMuscular,
                            series = seriesInt,
                            repeticiones = repeticionesInt,
                            pesoKg = pesoDouble,
                            fecha = fecha
                        )

                        db.rutinaDao().insertar(nuevaRutina)

                        onRutinaGuardada()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = azul
                )
            ) {
                Text(
                    text = "Guardar rutina",
                    color = Color.White
                )
            }
        }
    }
}