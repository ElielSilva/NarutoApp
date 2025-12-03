package com.example.narutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.narutoapp.ui.character.CharacterScreen
import com.example.narutoapp.ui.login.LoginScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) { LoginScreen(onLoginSuccess = {
            navController.navigate("home")
        }) }
        composable(Routes.Personagens.route) { CharacterScreen() }
        composable(Routes.Personagem.route) { CharacterScreen() }
        composable(Routes.Favoritos.route) { CharacterScreen() }
        composable(Routes.Vilas.route) { CharacterScreen() }
    }
}
