package com.example.narutoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.narutoapp.ui.character.CharacterScreen
import com.example.narutoapp.ui.character.CharacterViewModel
import com.example.narutoapp.ui.theme.NarutoAppTheme
import androidx.navigation.compose.composable
import com.example.narutoapp.navigation.BottomBar
import com.example.narutoapp.navigation.NavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NarutoAppTheme {
                NarutoAppTheme {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = { BottomBar(navController) }
                    ) { innerPadding ->
                        NavGraph(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
