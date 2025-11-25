package com.example.narutoapp.ui.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.narutoapp.utils.UiState
import coil.compose.AsyncImage
import com.example.narutoapp.models.AllCharacterItem

@Composable
fun CharacterScreen(
//    characterViewModel: CharacterViewModel
    characterViewModel: CharacterViewModel = viewModel()
//    characterViewModel: CharacterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by characterViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        color = MaterialTheme.colorScheme.onSurface
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

               LazyVerticalStaggeredGrid(
                   columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
                   modifier = Modifier.padding(horizontal = 16.dp),
                   verticalItemSpacing = 8.dp,
                   horizontalArrangement = Arrangement.spacedBy(8.dp)
               ) {
                   items(allCharacter) { character ->
                       CharacterItem(character = character)
                   }
               }
           }
       }
    }
}

@Composable
fun CharacterItem(character: AllCharacterItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    ) {
        Column(
            modifier =
                Modifier.padding(8.dp)
                    .fillMaxWidth()
                    .height(250.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                AsyncImage(
                    model = character.profile_image,
                    contentDescription = "image of $character.name",
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}