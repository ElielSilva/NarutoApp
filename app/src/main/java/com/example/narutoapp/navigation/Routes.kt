package com.example.narutoapp.navigation

sealed class Routes(val route: String) {
    object Personagens : Routes("Personagens")
    object Personagem : Routes("Personagem/{id}") {
        fun createRoute(id: Int) = "Personagem/$id"
    }
    object Favoritos : Routes("Favoritos")
    object Vilas : Routes("Vilas")
    object Login : Routes("Login")
}
