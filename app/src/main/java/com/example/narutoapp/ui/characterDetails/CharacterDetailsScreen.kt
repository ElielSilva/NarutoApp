package com.example.narutoapp.ui.characterDetails

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.narutoapp.R
import com.example.narutoapp.data.db.AppDatabase
import com.example.narutoapp.models.Character
import com.example.narutoapp.repository.character.CharacterRepositoryImpl
import com.example.narutoapp.services.ClientRetrofit
import com.example.narutoapp.utils.UiState

@Composable
fun CharacterDetailsScreen(
    id: Int
) {
    val factory = CharacterDetailsViewModelFactory(
        characterId = id, repository = CharacterRepositoryImpl(
            ClientRetrofit.getInstance(),
            AppDatabase.get(LocalContext.current).charactersDao()
        )
    )

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
                    character = (uiState as UiState.Success).data,
                    characterDetailsViewModel
                )
            }
        }
    }
}

@Composable
fun CharacterDetailsContent(
    character: Character,
    viewModel: CharacterDetailsViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    val isFavorite = uiState.let {
        if (it is UiState.Success) {
            Log.d("CharacterDetailsContent", "isFavorite: ${it.data}")
            it.data.isFavorite
        }
        else false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = character.images?.firstOrNull(),
                contentDescription = "Image of ${character.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                placeholder = painterResource(R.drawable.pexels_photo)
            )
            IconButton(
                onClick = { viewModel.toggleFavorite() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = character.name ?: "Unknown",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(20.dp))

        Text("Family", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        character.family?.forEach { (k, v) ->
            Text("${k.replaceFirstChar { it.uppercase() }}: $v")
        }

        Spacer(Modifier.height(20.dp))

        Text("Jutsu", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        character.jutsu?.take(10)?.forEach { Text("• $it") }
        character.jutsu?.size?.let { if (it > 10) Text("... +${character.jutsu?.size?.minus(10)} more") }

        Spacer(Modifier.height(20.dp))

        Text("Clã: ${character.clan}")

        Spacer(Modifier.height(20.dp))
    }
}