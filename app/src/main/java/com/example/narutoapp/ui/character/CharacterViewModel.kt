package com.example.narutoapp.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoapp.models.AllCharacter
import com.example.narutoapp.repository.character.CharacterRepositoryImpl
import com.example.narutoapp.services.ClientRetrofit
import com.example.narutoapp.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//class CharacterViewModel : ViewModel() {
//    private val _uiState = MutableStateFlow<UiState<AllCharacter>>(UiState.Loading)
//    val uiState = _uiState.asStateFlow()
//    private val _repository = CharacterRepository(ClientRetrofit)
//
//    init {
//        viewModelScope.launch {
//            try {
//                withContext(Dispatchers.IO) {
//                    fetch()
//                }
//            } catch (ex: Exception) {
//                _uiState.update {
//                    UiState.Error(ex.message.toString())
//                }
//            }
//        }
//    }
//
//    private suspend fun fetch() {
//
//        viewModelScope.launch {
//            try {
//                val data = withContext(Dispatchers.IO) {
//                    _repository.getAll()
//                }
//                if (data != null) {
//                    _uiState.update {
//                        UiState.Success(data)
//                    }
//                }
//            } catch (ex: Exception) {
//                _uiState.update {
//                    UiState.Error(ex.message.toString())
//                }
//            }
//        }
//
//
//    }
//}

class CharacterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AllCharacter>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val repository = CharacterRepositoryImpl(ClientRetrofit)

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repository.getAll()
                if (data != null) {
                    _uiState.value = UiState.Success(data)
                } else {
                    _uiState.value = UiState.Error("No data returned from repository")
                }
            } catch (ex: Exception) {
                _uiState.value = UiState.Error(ex.message ?: "Unknown error")
            }
        }
    }
}