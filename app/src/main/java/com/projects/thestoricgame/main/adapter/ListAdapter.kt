package com.projects.thestoricgame.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.thestoricgame.model.UserItem
import com.projects.thestoricgame.databinding.ChatsItemBinding
import com.projects.thestoricgame.viewholders.ChatsItemViewHolder

class ListAdapter(
    private val list: List<UserItem>,
    private val onClick: ((item: UserItem) -> Unit)?,
) : RecyclerView.Adapter<ChatsItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsItemViewHolder {
        return ChatsItemViewHolder(
            ChatsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ChatsItemViewHolder, position: Int) {
        holder.bind(list[position],onClick)
    }
}