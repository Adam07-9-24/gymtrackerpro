package com.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
                title = { Text("Agregar rutina", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2B579A)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Nueva rutina",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            CampoTextoRutina(
                label = "Ejercicio",
                value = ejercicio,
                placeholder = "Press banca"
            ) {
                ejercicio = it
            }

            CampoTextoRutina(
                label = "Grupo muscular",
                value = grupoMuscular,
                placeholder = "Pecho"
            ) {
                grupoMuscular = it
            }

            CampoTextoRutina(
                label = "Series",
                value = series,
                placeholder = "4",
                keyboardType = KeyboardType.Number
            ) {
                series = it
            }

            CampoTextoRutina(
                label = "Repeticiones",
                value = repeticiones,
                placeholder = "12",
                keyboardType = KeyboardType.Number
            ) {
                repeticiones = it
            }

            CampoTextoRutina(
                label = "Peso kg",
                value = pesoKg,
                placeholder = "50.5",
                keyboardType = KeyboardType.Decimal
            ) {
                pesoKg = it
            }

            CampoTextoRutina(
                label = "Fecha",
                value = fecha,
                placeholder = "15/05/2026"
            ) {
                fecha = it
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

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

                    if (seriesInt == null || repeticionesInt == null || pesoDouble == null) {
                        errorMessage = "Series, repeticiones y peso deben ser numéricos"
                        return@Button
                    }

                    scope.launch {
                        db.rutinaDao().insertar(
                            Rutina(
                                usuarioId = usuarioId,
                                ejercicio = ejercicio,
                                grupoMuscular = grupoMuscular,
                                series = seriesInt,
                                repeticiones = repeticionesInt,
                                pesoKg = pesoDouble,
                                fecha = fecha
                            )
                        )

                        onRutinaGuardada()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2B579A)
                )
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar rutina", color = Color.White)
            }
        }
    }
}

@Composable
fun CampoTextoRutina(
    label: String,
    value: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            )
        )
    }
}