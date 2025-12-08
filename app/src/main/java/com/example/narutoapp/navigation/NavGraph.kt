package com.example.narutoapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.narutoapp.ui.character.CharacterScreen
import com.example.narutoapp.ui.characterDetails.CharacterDetailsScreen
import com.example.narutoapp.ui.favorite.FavoriteScreen
import com.example.narutoapp.ui.login.LoginScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Personagens.route) {
            CharacterScreen(
                onPersonagemClick = { id ->
                    val destinationRoute = Routes.Personagem.createRoute(id)
                    Log.d("NAV_DEBUG", "Tentando ir pra: $destinationRoute")
                    navController.navigate(Routes.Personagem.createRoute(id))
                }
            )
        }

        composable(
            route = Routes.Personagem.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")!!
            CharacterDetailsScreen(id)
        }

//        composable(Routes.Favoritos.route) { CharacterScreen() }
//        composable(Routes.Vilas.route) { CharacterScreen() }
        composable(Routes.Login.route) {
            Log.d("NAV_DEBUG", "Tentando ir para tela de personagens após login")
            LoginScreen(onLoginSuccess = {
                navController.navigate("Personagens")
            })
        }

        composable(Routes.Favoritos.route) {
            FavoriteScreen()
        }
    }
}
