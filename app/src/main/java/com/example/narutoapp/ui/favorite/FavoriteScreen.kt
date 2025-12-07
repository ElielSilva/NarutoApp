package com.example.narutoapp.ui.favorite

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.narutoapp.models.AllCharacterItem
import com.example.narutoapp.ui.character.CharacterViewModel
import com.example.narutoapp.utils.UiState

@Composable
fun FavoriteScreen(
    characterViewModel: CharacterViewModel = viewModel()
) {
    val uiState by characterViewModel.uiState.collectAsState()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
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
                    Text(text = "Error: ${state.message}")
                }
            }

            is UiState.Success -> {
                val allCharacter = state.data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(allCharacter) { character ->
                        Log.d(
                            "PersonagemClick",
                            "ID ${character.id} clicado. Chamando onPersonagemClick."
                        )
                        FavoriteItem(
                            character = character
                        )
                    }
                }
            }
        }
    }
}

    @Composable
    fun FavoriteItem(
        character: AllCharacterItem
    ) {

        val context = LocalContext.current
        Spacer(modifier = Modifier.width(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
            Column(
                modifier =
                    Modifier.padding(8.dp)
                        .fillMaxWidth(),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(character.images.firstOrNull())
                            .crossfade(true)
                            .size(200)
                            .build(),
                        modifier = Modifier
                            .border(
                                BorderStroke(2.dp, MaterialTheme.colorScheme.surface),
                                shape = CircleShape
                            ).clip(
                                CircleShape
                            ),
                        contentDescription = "image of $character.name",
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.width(26.dp))
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }