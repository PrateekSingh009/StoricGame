package com.projects.thestoricgame.model

data class CharacterItem(
    val Name : String,
    val messages: Map<String,MessageItem>
) {
    constructor() : this("",mapOf())
}