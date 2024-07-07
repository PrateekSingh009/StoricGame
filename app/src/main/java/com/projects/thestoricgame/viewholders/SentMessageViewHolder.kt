package com.projects.thestoricgame.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.projects.thestoricgame.databinding.ListItemMessageSentBinding
import com.projects.thestoricgame.model.MessageItem

class SentMessageViewHolder(private val binding: ListItemMessageSentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MessageItem?) {
        item?.let{
            binding.apply {
                messageText.text = item.message
            }
        }
    }
}