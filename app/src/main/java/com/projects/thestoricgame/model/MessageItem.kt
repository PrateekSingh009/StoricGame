package com.projects.thestoricgame.model

data class MessageItem(
    val messageType : String,
    val choices : HashMap<String,Int>?,
    val message : String,
    val senderID : String?,
    val receiverID : String?
){
    constructor() : this("", hashMapOf(), "","","")
}