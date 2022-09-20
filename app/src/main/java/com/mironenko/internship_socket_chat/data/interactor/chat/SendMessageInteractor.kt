package com.mironenko.internship_socket_chat.data.interactor.chat

import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage
import com.mironenko.internship_socket_chat.ui.chat.UserChatAction
import com.mironenko.internship_socket_chat.ui.chat.UserChatState
import javax.inject.Inject

class SendMessageInteractor @Inject constructor(
    private val chatRepository: ChatRepository
) : Interactor<UserChatState, UserChatAction> {
    override suspend fun invoke(state: UserChatState, action: UserChatAction): UserChatAction {
        return if (action is UserChatAction.SendMessage) {
            val message = state.sendMessage
            val messageSend = ChatMessage(
                userId = state.receiverId,
                receiverId = chatRepository.userId,
                message = message
            )
            chatRepository.sendMessage(
                messageChat = messageSend
            )
            UserChatAction.ShowSentMessage(messageSend)
        } else {
            UserChatAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserChatAction): Boolean {
        return action is UserChatAction.SendMessage
    }
}