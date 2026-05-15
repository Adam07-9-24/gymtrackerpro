package com.gymtrackerpro.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                title = { Text("Nueva rutina", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción calendario */ }) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = "Calendario",
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
                .background(Color(0xFFF8F9FA))
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            
            CampoTextoPersonalizado(
                label = "Ejercicio",
                value = ejercicio,
                placeholder = "Press banca",
                onValueChange = { ejercicio = it }
            )

            CampoTextoPersonalizado(
                label = "Grupo muscular",
                value = grupoMuscular,
                placeholder = "Pecho",
                onValueChange = { grupoMuscular = it },
                trailingIcon = {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Gray)
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    CampoTextoPersonalizado(
                        label = "Series",
                        value = series,
                        placeholder = "4",
                        keyboardType = KeyboardType.Number,
                        onValueChange = { series = it }
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CampoTextoPersonalizado(
                        label = "Repeticiones",
                        value = repeticiones,
                        placeholder = "12",
                        keyboardType = KeyboardType.Number,
                        onValueChange = { repeticiones = it }
                    )
                }
            }

            CampoTextoPersonalizado(
                label = "Peso (kg)",
                value = pesoKg,
                placeholder = "60.5",
                keyboardType = KeyboardType.Decimal,
                onValueChange = { pesoKg = it }
            )

            CampoTextoPersonalizado(
                label = "Fecha",
                value = fecha,
                placeholder = "12/05/2026",
                onValueChange = { fecha = it },
                trailingIcon = {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2B579A)
                )
            ) {
                Text(
                    "Guardar rutina",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CampoTextoPersonalizado(
    label: String,
    value: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.Gray.copy(alpha = 0.5f)) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2B579A),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}
