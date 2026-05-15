package com.gymtrackerpro.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
fun DetalleRutinaScreen(
    rutinaId: Int,
    onBack: () -> Unit,
    onRutinaActualizada: () -> Unit,
    onRutinaEliminada: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val scope = rememberCoroutineScope()

    var rutinaActual by remember { mutableStateOf<Rutina?>(null) }

    var ejercicio by remember { mutableStateOf("") }
    var grupoMuscular by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticiones by remember { mutableStateOf("") }
    var pesoKg by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }

    LaunchedEffect(rutinaId) {
        val rutina = db.rutinaDao().buscarPorId(rutinaId)
        rutinaActual = rutina

        if (rutina != null) {
            ejercicio = rutina.ejercicio
            grupoMuscular = rutina.grupoMuscular
            series = rutina.series.toString()
            repeticiones = rutina.repeticiones.toString()
            pesoKg = rutina.pesoKg.toString()
            fecha = rutina.fecha
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar rutina", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            mostrarDialogoEliminar = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
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
            CampoEditarRutina(
                label = "Ejercicio",
                value = ejercicio,
                placeholder = "Press banca"
            ) {
                ejercicio = it
            }

            CampoEditarRutina(
                label = "Grupo muscular",
                value = grupoMuscular,
                placeholder = "Pecho"
            ) {
                grupoMuscular = it
            }

            CampoEditarRutina(
                label = "Series",
                value = series,
                placeholder = "4",
                keyboardType = KeyboardType.Number
            ) {
                series = it
            }

            CampoEditarRutina(
                label = "Repeticiones",
                value = repeticiones,
                placeholder = "12",
                keyboardType = KeyboardType.Number
            ) {
                repeticiones = it
            }

            CampoEditarRutina(
                label = "Peso kg",
                value = pesoKg,
                placeholder = "50.5",
                keyboardType = KeyboardType.Decimal
            ) {
                pesoKg = it
            }

            CampoEditarRutina(
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
                    val rutina = rutinaActual

                    if (rutina == null) {
                        errorMessage = "No se encontró la rutina"
                        return@Button
                    }

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
                        val rutinaEditada = rutina.copy(
                            ejercicio = ejercicio,
                            grupoMuscular = grupoMuscular,
                            series = seriesInt,
                            repeticiones = repeticionesInt,
                            pesoKg = pesoDouble,
                            fecha = fecha
                        )

                        db.rutinaDao().actualizar(rutinaEditada)
                        onRutinaActualizada()
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
                Text("Guardar cambios", color = Color.White)
            }
        }
    }

    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogoEliminar = false
            },
            title = {
                Text("Eliminar rutina")
            },
            text = {
                Text("¿Seguro que deseas eliminar esta rutina?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val rutina = rutinaActual

                        if (rutina != null) {
                            scope.launch {
                                db.rutinaDao().eliminar(rutina)
                                mostrarDialogoEliminar = false
                                onRutinaEliminada()
                            }
                        }
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoEliminar = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun CampoEditarRutina(
    label: String,
    value: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label)

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(placeholder)
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            )
        )
    }
}