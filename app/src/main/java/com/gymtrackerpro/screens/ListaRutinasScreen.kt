package com.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gymtrackerpro.data.AppDatabase
import com.gymtrackerpro.data.Rutina
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRutinasScreen(
    usuarioId: Int,
    onNavigateToAgregar: () -> Unit,
    onNavigateToDetalle: (Int) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val scope = rememberCoroutineScope()

    val azul = Color(0xFF2B579A)

    var rutinas by remember { mutableStateOf<List<Rutina>>(emptyList()) }
    var rutinaAEliminar by remember { mutableStateOf<Rutina?>(null) }

    fun cargarRutinas() {
        scope.launch {
            rutinas = db.rutinaDao().listarPorUsuario(usuarioId)
        }
    }

    LaunchedEffect(usuarioId) {
        cargarRutinas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis rutinas",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAgregar,
                containerColor = azul,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar rutina"
                )
            }
        }
    ) { padding ->

        if (rutinas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Aún no tienes rutinas registradas")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(rutinas) { rutina ->
                    RutinaItem(
                        rutina = rutina,
                        onEdit = {
                            onNavigateToDetalle(rutina.id)
                        },
                        onDelete = {
                            rutinaAEliminar = rutina
                        }
                    )
                }
            }
        }
    }

    if (rutinaAEliminar != null) {
        AlertDialog(
            onDismissRequest = {
                rutinaAEliminar = null
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
                        val rutina = rutinaAEliminar

                        if (rutina != null) {
                            scope.launch {
                                db.rutinaDao().eliminar(rutina)
                                rutinaAEliminar = null
                                cargarRutinas()
                            }
                        }
                    }
                ) {
                    Text(
                        text = "Eliminar",
                        color = Color.Red
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        rutinaAEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun RutinaItem(
    rutina: Rutina,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = rutina.ejercicio,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text("Grupo muscular: ${rutina.grupoMuscular}")
            Text("Series: ${rutina.series}")
            Text("Repeticiones: ${rutina.repeticiones}")
            Text("Peso: ${rutina.pesoKg} kg")
            Text("Fecha: ${rutina.fecha}")

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Button(
                    onClick = onEdit
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Editar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}