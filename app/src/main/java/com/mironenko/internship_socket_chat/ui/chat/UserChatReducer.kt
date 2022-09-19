package com.mironenko.internship_socket_chat.ui.chat

import com.mironenko.internship_socket_chat.base.Reducer

class UserChatReducer(
    receiverId: String
) : Reducer<UserChatState, UserChatAction> {

    override val initialState = UserChatState(
        messages = listOf(),
        receiverId = receiverId
    )

    override fun reduce(state: UserChatState, action: UserChatAction): UserChatState {
        return when (action) {
            UserChatAction.None -> state.copy(
                isLoading = false
            )
            is UserChatAction.ShowReceivedMessage -> state.copy(
                messages = state.messages + action.chatMessage
            )
            is UserChatAction.ShowSentMessage -> state.copy(
                messages = state.messages + action.chatMessage
            )
            is UserChatAction.SetMessage -> state.copy(
                sendMessage = action.message
            )
            is UserChatAction.SendMessage -> state.copy(
                isLoading = true
            )
            is UserChatAction.NewMessageReceived -> state.copy(
                isLoading = true
            )
            is UserChatAction.Error -> state.copy(
                errorMessage = action.error.toString()
            )
        }
    }
}