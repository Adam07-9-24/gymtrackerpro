package com.gymtrackerpro.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuPrincipalScreen(
    usuarioId: Int,
    onNavigateToAgregarRutina: () -> Unit,
    onNavigateToListaRutinas: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Menú principal")
        Text(text = "Usuario ID: $usuarioId")

        Button(onClick = onNavigateToAgregarRutina) {
            Text("Agregar rutina")
        }

        Button(onClick = onNavigateToListaRutinas) {
            Text("Ver rutinas")
        }

        Button(onClick = onNavigateToPerfil) {
            Text("Perfil de usuario")
        }

        Button(onClick = onLogout) {
            Text("Cerrar sesión")
        }
    }
}