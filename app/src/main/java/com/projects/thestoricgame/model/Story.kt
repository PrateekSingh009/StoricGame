package com.projects.thestoricgame.model

data class Story(
    val Chapters: Map<String,Chapter>
) {
    constructor() : this(mapOf())
}
