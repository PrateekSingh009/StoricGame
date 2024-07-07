package com.projects.thestoricgame.model


data class UserItem(
    val name : String,
    val image : String
)
{
    constructor() : this("","")
}