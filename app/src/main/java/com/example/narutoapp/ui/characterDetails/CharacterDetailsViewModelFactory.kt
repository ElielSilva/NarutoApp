package com.example.narutoapp.ui.characterDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.narutoapp.repository.character.ICharacterRepository

class CharacterDetailsViewModelFactory(
    private val characterId: Int,
    private val repository: ICharacterRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterDetailsViewModel(characterId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}