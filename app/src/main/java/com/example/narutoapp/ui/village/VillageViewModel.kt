package com.example.narutoapp.ui.village

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoapp.models.Village
import com.example.narutoapp.repository.village.IVillageRepository
import com.example.narutoapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VillageViewModel(
    private val repository: IVillageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ArrayList<Village>?>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                Log.d("VillageViewModel", "Iniciando busca de personagens...")
                val villages = repository.getAll()
                Log.d("VillageViewModel", villages.toString())
                _uiState.value = UiState.Success(villages)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}