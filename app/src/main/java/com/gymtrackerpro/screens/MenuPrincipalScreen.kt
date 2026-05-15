package com.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gymtrackerpro.data.AppDatabase
import com.gymtrackerpro.data.Usuario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(
    usuarioId: Int,
    onNavigateToAgregarRutina: () -> Unit,
    onNavigateToListaRutinas: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    onLogout: () -> Unit
) {
    val azul = Color(0xFF1565C0)

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var usuario by remember { mutableStateOf<Usuario?>(null) }

    LaunchedEffect(usuarioId) {
        usuario = db.usuarioDao().buscarPorId(usuarioId)
    }

    val nombreUsuario = usuario?.nombreCompleto ?: "Usuario"
    val emailUsuario = usuario?.email ?: "Sin email"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = nombreUsuario,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = emailUsuario,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider()

                    NavigationDrawerItem(
                        icon = {
                            Icon(Icons.Default.Home, contentDescription = null)
                        },
                        label = {
                            Text("Inicio")
                        },
                        selected = true,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )

                    NavigationDrawerItem(
                        icon = {
                            Icon(Icons.Default.Add, contentDescription = null)
                        },
                        label = {
                            Text("Agregar rutina")
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            onNavigateToAgregarRutina()
                        }
                    )

                    NavigationDrawerItem(
                        icon = {
                            Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)
                        },
                        label = {
                            Text("Mis rutinas")
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            onNavigateToListaRutinas()
                        }
                    )

                    NavigationDrawerItem(
                        icon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        label = {
                            Text("Mi perfil")
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            onNavigateToPerfil()
                        }
                    )

                    NavigationDrawerItem(
                        icon = {
                            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                        },
                        label = {
                            Text("Cerrar sesión")
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            onLogout()
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "GymTracker Pro",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
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
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bienvenido",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = nombreUsuario,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onNavigateToAgregarRutina,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = azul
                    )
                ) {
                    Text("Agregar rutina")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onNavigateToListaRutinas,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = azul
                    )
                ) {
                    Text("Mis rutinas")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onNavigateToPerfil,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = azul
                    )
                ) {
                    Text("Mi perfil")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Cerrar sesión")
                }
            }
        }
    }
}