package com.example.narutoapp.ui.characterDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoapp.repository.character.CharacterRepositoryImpl
import com.example.narutoapp.services.ClientRetrofit
import com.example.narutoapp.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(private val id: Int) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AllCharacterItem>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val repository = CharacterRepositoryImpl(ClientRetrofit)


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


            _uiState.value = UiState.Success(updated)


            // repository.saveFavorite(updated)
        }
    }
}