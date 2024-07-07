package com.projects.thestoricgame.model

data class MessageItem(
    val message : String,
    val senderID : String,
    val receiverID : String
){
    constructor() : this("","","")
}