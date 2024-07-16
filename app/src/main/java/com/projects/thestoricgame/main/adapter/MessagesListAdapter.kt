package com.projects.thestoricgame.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.thestoricgame.databinding.ListItemMessageReceivedBinding
import com.projects.thestoricgame.databinding.ListItemMessageSentBinding
import com.projects.thestoricgame.model.MessageItem
import com.projects.thestoricgame.utils.callbacks.MessageDiffCallback
import com.projects.thestoricgame.utils.utility.AppConstants
import com.projects.thestoricgame.viewholders.ReceivedMessageViewHolder
import com.projects.thestoricgame.viewholders.SentMessageViewHolder

class MessagesListAdapter(val showDialog : (MessageItem) -> Unit) :
    ListAdapter<MessageItem, RecyclerView.ViewHolder>(
        MessageDiffCallback()
    ) {

    private val holderTypeMessageReceived = 1
    private val holderTypeMessageSent = 2
    private val holderTypeChoice = 3

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).messageType) {
            AppConstants.TYPE_MESSAGE -> typeOfMessage(position)
            AppConstants.TYPE_CHOICE -> holderTypeChoice
            else -> typeOfMessage(position)
        }
    }

    private fun typeOfMessage(position : Int) : Int {
        return if (getItem(position).senderID != AppConstants.USER_ID) {
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

//            holderTypeChoice -> showDialog.invoke(getItem(position))
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

            holderTypeChoice -> object : RecyclerView.ViewHolder(View(parent.context)) {}

            else -> {
                throw Exception("Error reading holder type")
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.itemViewType == holderTypeChoice) {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                showDialog(getItem(position))
            }
        }
    }
}

