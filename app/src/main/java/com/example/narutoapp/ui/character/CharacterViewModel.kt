package com.example.narutoapp.ui.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoapp.repository.character.ICharacterRepository
import com.example.narutoapp.models.Character
import com.example.narutoapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: ICharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ArrayList<Character>?>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                Log.d("CharacterViewModel", "Iniciando busca de personagens...")
                val characters = repository.getAll()
                Log.d("CharacterViewModel", characters.toString())
                _uiState.value = UiState.Success(characters)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(id)
                loadCharacters()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Erro ao favoritar")
            }
        }
    }
}