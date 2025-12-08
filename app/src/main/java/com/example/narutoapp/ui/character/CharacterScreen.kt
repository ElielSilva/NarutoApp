package com.example.narutoapp.ui.character

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.narutoapp.data.db.AppDatabase
import com.example.narutoapp.models.Character
import com.example.narutoapp.repository.character.CharacterRepositoryImpl
import com.example.narutoapp.services.ClientRetrofit
import com.example.narutoapp.utils.UiState
import com.example.narutoapp.R

@Composable
fun CharacterScreen(
    onPersonagemClick: (id: Int) -> Unit = {},
    characterViewModel: CharacterViewModel = viewModel(
        factory = CharacterViewModelFactory(
            CharacterRepositoryImpl(
                ClientRetrofit.getInstance(),
                AppDatabase.get(LocalContext.current).charactersDao()
            )
        )
    )
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
               val allCharacter = state.data as List<Character>

               LazyColumn(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(horizontal = 8.dp, vertical = 8.dp),
                   verticalArrangement = Arrangement.spacedBy(12.dp),
                   contentPadding = PaddingValues(8.dp)
               ) {
                   items(items = allCharacter) { character ->
                       Log.d("PersonagemClick", "ID ${character.id} clicado. Chamando onPersonagemClick.")
                       CharacterItem(
                           character = character,
                           onPersonagemClick = onPersonagemClick
                       )
                   }
               }
           }
       }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    onPersonagemClick: (id: Int) -> Unit
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
        Row(
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        Log.d(
                            "PersonagemClick",
                            "ID ${character.id} clicado. Chamando onPersonagemClick."
                        )
                        onPersonagemClick(character.id)
                    },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(character.images?.firstOrNull())
                        .crossfade(true)
                        .size(70)
                        .build(),
                    modifier = Modifier
                        .border(
                            BorderStroke(2.dp, MaterialTheme.colorScheme.surface),
                            shape = CircleShape
                        )
                        .size(70.dp)
                        .clip(
                            CircleShape
                        ),
                    placeholder = painterResource(R.drawable.pexels_photo),
                    error = painterResource(R.drawable.pexels_photo),
                    fallback = painterResource(R.drawable.pexels_photo),
                    contentDescription = "image of $character.name",
                    alignment = Alignment.Center
                )
                Spacer(modifier = Modifier.width(26.dp))
                Text(
                    text = character.name ?: "Nome Desconhecido",
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}