package com.mironenko.internship_socket_chat.domain.interactors.chat

import com.mironenko.internship_socket_chat.domain.ChatRepository
import com.mironenko.internship_socket_chat.domain.actions.UserChatAction
import com.mironenko.internship_socket_chat.domain.base.Interactor
import com.mironenko.internship_socket_chat.domain.models.ChatMessage
import com.mironenko.internship_socket_chat.domain.states.UserChatState

class SendMessageInteractor(
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