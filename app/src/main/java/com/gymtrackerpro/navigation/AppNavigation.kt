package com.gymtrackerpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gymtrackerpro.screens.LoginScreen
import com.gymtrackerpro.screens.RegistroScreen
import com.gymtrackerpro.screens.MenuPrincipalScreen
import com.gymtrackerpro.screens.AgregarRutinaScreen
import com.gymtrackerpro.screens.ListaRutinasScreen
import com.gymtrackerpro.screens.DetalleRutinaScreen
import com.gymtrackerpro.screens.PerfilUsuarioScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { usuarioId ->
                    navController.navigate(Screen.MenuPrincipal.createRoute(usuarioId)) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate(Screen.Registro.route)
                }
            )
        }

        composable(Screen.Registro.route) {
            RegistroScreen(
                onRegistroSuccess = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.MenuPrincipal.route,
            arguments = listOf(
                navArgument("usuarioId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            MenuPrincipalScreen(
                usuarioId = usuarioId,
                onNavigateToAgregarRutina = {
                    navController.navigate(Screen.AgregarRutina.createRoute(usuarioId))
                },
                onNavigateToListaRutinas = {
                    navController.navigate(Screen.ListaRutinas.createRoute(usuarioId))
                },
                onNavigateToPerfil = {
                    navController.navigate(Screen.PerfilUsuario.createRoute(usuarioId))
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(
            route = Screen.AgregarRutina.route,
            arguments = listOf(
                navArgument("usuarioId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            AgregarRutinaScreen(
                usuarioId = usuarioId,
                onRutinaGuardada = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.ListaRutinas.route,
            arguments = listOf(
                navArgument("usuarioId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            ListaRutinasScreen(
                usuarioId = usuarioId,
                onNavigateToAgregar = {
                    navController.navigate(Screen.AgregarRutina.createRoute(usuarioId))
                },
                onNavigateToDetalle = { rutinaId ->
                    navController.navigate(Screen.DetalleRutina.createRoute(rutinaId))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.DetalleRutina.route,
            arguments = listOf(
                navArgument("rutinaId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val rutinaId = backStackEntry.arguments?.getInt("rutinaId") ?: 0

            DetalleRutinaScreen(
                rutinaId = rutinaId,
                onBack = {
                    navController.popBackStack()
                },
                onRutinaActualizada = {
                    navController.popBackStack()
                },
                onRutinaEliminada = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.PerfilUsuario.route,
            arguments = listOf(
                navArgument("usuarioId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            PerfilUsuarioScreen(
                usuarioId = usuarioId,
                onBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}