package com.mironenko.internship_socket_chat.ui.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage
import com.mironenko.internship_socket_chat.databinding.LayoutReceivedMessageItemBinding
import com.mironenko.internship_socket_chat.databinding.LayoutSendMessageItemBinding

class UserChatAdapter(
    private val receiveId: String
) : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    enum class TypeItem(val type: Int) {
        SEND(1), RECEIVE(2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TypeItem.SEND.type -> SendMessageViewHolder(
                LayoutSendMessageItemBinding.inflate(inflater, parent, false)
            )
            TypeItem.RECEIVE.type -> ReceivedMessageViewHolder(
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
        Log.d("TAG", "getItemViewType $position, list size ${currentList.size}")
        return if (message.id == receiveId) {
            TypeItem.RECEIVE.type
        } else {
            TypeItem.SEND.type
        }
    }

    inner class SendMessageViewHolder(
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
}