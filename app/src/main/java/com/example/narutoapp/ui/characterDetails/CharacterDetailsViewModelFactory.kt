package com.example.narutoapp.ui.characterDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CharacterDetailsViewModelFactory(
    private val characterId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterDetailsViewModel(characterId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}