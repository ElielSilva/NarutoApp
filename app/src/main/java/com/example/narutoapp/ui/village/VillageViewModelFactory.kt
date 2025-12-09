package com.example.narutoapp.ui.village

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.narutoapp.repository.village.IVillageRepository
import com.example.narutoapp.ui.character.CharacterViewModel

class VillageViewModelFactory(
    private val repository: IVillageRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VillageViewModel::class.java)) {
            return VillageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}