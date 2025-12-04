package com.example.narutoapp.ui.characterDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.narutoapp.models.AllCharacterItem


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.narutoapp.utils.UiState

@Composable
fun CharacterDetailsScreen(
    id: Int,
//    characterDetailsViewModel: CharacterDetailsViewModel = viewModel()
) {

    // essa bosta de view não recebar o id diretamente, não apaga essa fabrica que foi a tarde toda pra fazer.
    val factory = CharacterDetailsViewModelFactory(characterId = id)

    val characterDetailsViewModel: CharacterDetailsViewModel = viewModel(factory = factory)

    val uiState by characterDetailsViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onSurface
    ) {
        when (uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${(uiState as UiState.Error).message}")
                }
            }

            is UiState.Success -> {
                CharacterDetailsContent(
                    character = (uiState as UiState.Success).data
                )
            }
        }
    }
}

@Composable
fun CharacterDetailsContent(character: AllCharacterItem) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            model = character.images.firstOrNull(),
            contentDescription = "Image of ${character.name}",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(20.dp))

        Text("Family", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        character.family.forEach { (k, v) ->
            Text("${k.replaceFirstChar { it.uppercase() }}: $v")
        }

        Spacer(Modifier.height(20.dp))

        Text("Jutsu", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        character.jutsu.take(10).forEach { Text("• $it") }
        if (character.jutsu.size > 10) Text("... +${character.jutsu.size - 10} more")

        Spacer(Modifier.height(20.dp))
        Text("Clã: ${character.clan}")

        Spacer(Modifier.height(20.dp))
    }
}
