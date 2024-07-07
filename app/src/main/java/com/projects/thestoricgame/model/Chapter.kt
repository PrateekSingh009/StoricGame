package com.projects.thestoricgame.model

data class Chapter(
    val characters: Map<String, CharacterItem>
) {
    constructor() : this(mapOf())
}