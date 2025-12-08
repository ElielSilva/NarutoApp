package com.example.narutoapp.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.narutoapp.repository.character.ICharacterRepository

class CharacterViewModelFactory(
    private val repository: ICharacterRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}