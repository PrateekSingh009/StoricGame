package com.projects.thestoricgame.model

data class Chapter(
    val messages: List<MessageItem>
) {
    constructor() : this(listOf())
}