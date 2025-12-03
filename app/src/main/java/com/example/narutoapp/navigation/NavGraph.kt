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

@Composable
fun NavGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        startDestination = Routes.Personagens.route
//    ) {
//        composable(Routes.Personagens.route) { CharacterScreen() }
//        composable(Routes.Personagem.route) { CharacterScreen() }
//        composable(Routes.Favoritos.route) { CharacterScreen() }
//        composable(Routes.Vilas.route) { CharacterScreen() }
//    }
    NavHost(
        navController = navController,
        startDestination = Routes.Personagens.route
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
    }
}
