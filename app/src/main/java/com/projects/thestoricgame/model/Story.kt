package com.projects.thestoricgame.model

data class Story(
    val chapters: Map<String,Chapter>
) {
    constructor() : this(mapOf())
}
