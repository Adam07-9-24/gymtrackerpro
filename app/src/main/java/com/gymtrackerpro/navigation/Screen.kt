package com.gymtrackerpro.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")

    object Registro : Screen("registro")

    object MenuPrincipal : Screen("menu/{usuarioId}") {
        fun createRoute(usuarioId: Int): String = "menu/$usuarioId"
    }

    object AgregarRutina : Screen("agregar_rutina/{usuarioId}") {
        fun createRoute(usuarioId: Int): String = "agregar_rutina/$usuarioId"
    }

    object ListaRutinas : Screen("lista_rutinas/{usuarioId}") {
        fun createRoute(usuarioId: Int): String = "lista_rutinas/$usuarioId"
    }

    object DetalleRutina : Screen("detalle_rutina/{rutinaId}") {
        fun createRoute(rutinaId: Int): String = "detalle_rutina/$rutinaId"
    }

    object PerfilUsuario : Screen("perfil/{usuarioId}") {
        fun createRoute(usuarioId: Int): String = "perfil/$usuarioId"
    }
}