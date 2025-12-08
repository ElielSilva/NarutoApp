package com.example.narutoapp.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoapp.data.entity.toModel
import com.example.narutoapp.models.Character
import com.example.narutoapp.repository.character.ICharacterRepository
import com.example.narutoapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
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
                Log.d("FavoriteViewModel", "Iniciando busca de personagens...")
                val characters = repository.getFavorites()
                Log.d("FavoriteViewModel", characters.toString())
                val data = characters?.map { it.toModel() } as ArrayList<Character>?
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}