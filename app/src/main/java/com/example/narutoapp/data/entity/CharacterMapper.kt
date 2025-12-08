package com.example.narutoapp.data.entity

import com.example.narutoapp.data.entity.CharactersEntity
import com.example.narutoapp.models.Character

fun CharactersEntity.toModel(): Character {
    return Character(
        id = id,
        name = name,
        images = images,
        family = family,
        jutsu = jutsu,
        clan = clan,
        isFavorite = isFavorite
    )
}

fun Character.toEntity(): CharactersEntity {
    return CharactersEntity(
        id = id,
        name = name,
        images = images,
        family = family,
        jutsu = jutsu,
        clan = clan,
        isFavorite = isFavorite
    )
}
