package com.example.narutoapp.ui.characterDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoapp.models.Character
import com.example.narutoapp.repository.character.ICharacterRepository
import com.example.narutoapp.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val id: Int,
    private val repository: ICharacterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Character>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetch()
    }
    private fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("CharacterViewModel", "Buscando personagens da API...")
                val data = repository.getById(id)
                Log.d("CharacterViewModel", id.toString())
                Log.d("CharacterViewModel", data.toString())
                if (data != null) {
                    _uiState.value = UiState.Success(data)
                } else {
                    _uiState.value = UiState.Error("No data returned from repository")
                }
            } catch (ex: Exception) {
                Log.e("CharacterViewModel", "Erro ao buscar personagens", ex)
                _uiState.value = UiState.Error(ex.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite() {
        val state = _uiState.value
        if (state is UiState.Success) {

            val updated = state.data.copy(
                isFavorite = !state.data.isFavorite
            )
            viewModelScope.launch(Dispatchers.IO) {
                repository.toggleFavorite(updated.id)
            }

            _uiState.value = UiState.Success(updated)
        }
    }
}