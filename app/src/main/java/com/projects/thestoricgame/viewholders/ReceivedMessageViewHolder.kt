package com.projects.thestoricgame.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.projects.thestoricgame.databinding.ListItemMessageReceivedBinding
import com.projects.thestoricgame.model.MessageItem

class ReceivedMessageViewHolder(private val binding: ListItemMessageReceivedBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MessageItem?) {
        item?.let{
            binding.apply {
                messageText.text = item.message
            }
        }
    }
}