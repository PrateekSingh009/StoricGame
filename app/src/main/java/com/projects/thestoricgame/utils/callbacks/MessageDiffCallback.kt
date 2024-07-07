package com.projects.thestoricgame.utils.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.projects.thestoricgame.model.MessageItem

class MessageDiffCallback : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.message == newItem.message
    }
}
