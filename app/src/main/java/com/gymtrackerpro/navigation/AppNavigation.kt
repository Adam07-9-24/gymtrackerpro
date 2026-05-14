package com.gymtrackerpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gymtrackerpro.screens.LoginScreen
import com.gymtrackerpro.screens.RegistroScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { 
            LoginScreen(
                onLoginSuccess = { userId -> 
                    // El laboratorio pide que al iniciar sesión correctamente devuelva el usuarioId
                    // Por ahora solo imprimimos, ya que MenuPrincipal no es parte de esta entrega
                    println("Login exitoso. Usuario ID: $userId")
                },
                onNavigateToRegistro = { navController.navigate("registro") }
            )
        }
        composable("registro") {
            RegistroScreen(
                onRegistroSuccess = { navController.popBackStack() },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
    }
}
