package com.example.narutoapp.ui.characterDetails

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
        color = MaterialTheme.colorScheme.background
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
                modifier = Modifier.fillMaxSize(),
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
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(20.dp))

        val familyLabels = mapOf(
            "father" to "Pai",
            "mother" to "Mãe",
            "brother" to "Irmão",
            "sister" to "Irmã",
            "son" to "Filho",
            "nephew" to "Sobrinho",
            "adoptive" to "Adotivo",
            "uncle" to "Tio",
            "lover" to "Amante",
            "grandmother" to "Avó",
            "granduncle" to "Tio-avô",
            "grandfather" to "Avô",
            "great-grandfather" to "Bisavô",
            "clone/son" to "Clone/Filho"
        )

        Text("Familia", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)

        if (character.family.isNullOrEmpty()) {
            Text(
                text = "Afiliação desconhecida",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            character.family.forEach { (k, v) ->
                val label = familyLabels[k.lowercase()] ?: k.replaceFirstChar { it.uppercase() }
                Text("$label: $v", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Jutsus", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)

        character.jutsu?.forEach { jutsu ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Black.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = jutsu,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Clã", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)

        if (character.clan.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Black.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = "Desconhecido",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        } else {
            character.clan.forEach { clan ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.Black.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .padding(bottom = 4.dp)
                ) {
                    Text(
                        text = clan,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))
    }
}