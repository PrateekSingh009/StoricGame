package com.projects.thestoricgame.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.projects.thestoricgame.model.UserItem
import com.projects.thestoricgame.databinding.ChatsItemBinding

class ChatsItemViewHolder(private val binding: ChatsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UserItem?, onClick : ((item: UserItem) -> Unit)?) {
        item?.let{
            binding.apply {
                displayNameText.text = item.name
            }
            itemView.setOnClickListener {
                onClick?.invoke(item)
            }
        }
    }
}