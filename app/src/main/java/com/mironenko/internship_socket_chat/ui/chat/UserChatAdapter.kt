package com.mironenko.internship_socket_chat.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage
import com.mironenko.internship_socket_chat.databinding.LayoutReceivedMessageItemBinding
import com.mironenko.internship_socket_chat.databinding.LayoutSendMessageItemBinding

class UserChatAdapter(
    private val isSentMessage: (author: String) -> Boolean
) : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TypeItem.SEND.ordinal -> SendMessageViewHolder(
                LayoutSendMessageItemBinding.inflate(inflater, parent, false)
            )
            TypeItem.RECEIVE.ordinal -> ReceivedMessageViewHolder(
                LayoutReceivedMessageItemBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Wrong Type View Holder $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = currentList[position]
        when (holder) {
            is SendMessageViewHolder -> holder.bind(message = message)
            is ReceivedMessageViewHolder -> holder.bind(message = message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = currentList[position]
        return if (isSentMessage(message.receiverId)) {
            TypeItem.RECEIVE.ordinal
        } else {
            TypeItem.SEND.ordinal
        }
    }

    class SendMessageViewHolder(
        private val binding: LayoutSendMessageItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.tvSendMessage.text = message.message
        }
    }

    class ReceivedMessageViewHolder(
        private val binding: LayoutReceivedMessageItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.tvReceivedMessage.text = message.message
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.messageUUID == newItem.messageUUID
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == oldItem
        }
    }

    enum class TypeItem {
        SEND, RECEIVE
    }
}