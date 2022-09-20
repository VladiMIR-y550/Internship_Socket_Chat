package com.mironenko.internship_socket_chat.data.interactor.chat

import com.mironenko.internship_socket_chat.base.SideEffectInteractor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.chat.UserChatAction
import com.mironenko.internship_socket_chat.ui.chat.UserChatState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNewMessageInteractor @Inject constructor(
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