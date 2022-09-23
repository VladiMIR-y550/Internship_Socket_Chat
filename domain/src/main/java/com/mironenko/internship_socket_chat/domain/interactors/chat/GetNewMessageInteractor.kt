package com.mironenko.internship_socket_chat.domain.interactors.chat

import com.mironenko.internship_socket_chat.domain.ChatRepository
import com.mironenko.internship_socket_chat.domain.actions.UserChatAction
import com.mironenko.internship_socket_chat.domain.base.SideEffectInteractor
import com.mironenko.internship_socket_chat.domain.states.UserChatState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNewMessageInteractor(
    private val chatRepository: ChatRepository
) : SideEffectInteractor<UserChatState, UserChatAction> {

    override val sideEffectFlow: Flow<UserChatAction>
        get() = chatRepository.messages.map { message ->
            UserChatAction.NewMessageReceived(message)
        }

    override suspend fun invoke(state: UserChatState, action: UserChatAction): UserChatAction {
        return if (action is UserChatAction.NewMessageReceived) {
            if (action.chatMessage.receiverId == state.receiverId) {
                UserChatAction.ShowReceivedMessage(action.chatMessage)
            } else {
                UserChatAction.None
            }
        } else {
            UserChatAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserChatAction): Boolean {
        return action is UserChatAction.NewMessageReceived
    }
}