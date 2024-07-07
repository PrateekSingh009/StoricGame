package com.projects.thestoricgame.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.thestoricgame.databinding.ListItemMessageReceivedBinding
import com.projects.thestoricgame.databinding.ListItemMessageSentBinding
import com.projects.thestoricgame.model.MessageItem
import com.projects.thestoricgame.utils.callbacks.MessageDiffCallback
import com.projects.thestoricgame.viewholders.ReceivedMessageViewHolder
import com.projects.thestoricgame.viewholders.SentMessageViewHolder

class MessagesListAdapter(private val userId: String) :
    ListAdapter<MessageItem, RecyclerView.ViewHolder>(
        MessageDiffCallback()
    ) {

    private val holderTypeMessageReceived = 1
    private val holderTypeMessageSent = 2

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).senderID != userId) {
            holderTypeMessageReceived
        } else {
            holderTypeMessageSent
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            holderTypeMessageSent -> (holder as SentMessageViewHolder).bind(
                getItem(position)
            )

            holderTypeMessageReceived -> (holder as ReceivedMessageViewHolder).bind(
                getItem(position)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            holderTypeMessageSent -> {
                val binding = ListItemMessageSentBinding.inflate(layoutInflater, parent, false)
                SentMessageViewHolder(binding)
            }

            holderTypeMessageReceived -> {
                val binding = ListItemMessageReceivedBinding.inflate(layoutInflater, parent, false)
                ReceivedMessageViewHolder(binding)
            }

            else -> {
                throw Exception("Error reading holder type")
            }
        }
    }
}

