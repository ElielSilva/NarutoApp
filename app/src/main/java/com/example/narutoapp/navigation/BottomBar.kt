package com.example.narutoapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Routes.Personagens.route,
            onClick = { navController.navigate(Routes.Personagens.route) },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Personagens") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.Personagem.route,
            onClick = { navController.navigate(Routes.Personagens.route) },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Personagem") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.Favoritos.route,
            onClick = { navController.navigate(Routes.Favoritos.route) },
            icon = { Icon(Icons.Default.Star, null) },
            label = { Text("Favoritos") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.Vilas.route,
            onClick = { navController.navigate(Routes.Vilas.route) },
            icon = { Icon(Icons.Default.LocationCity, null) },
            label = { Text("Vilas") }
        )
    }
}
