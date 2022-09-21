package com.mironenko.internship_socket_chat.data.interactor.user_list

import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.user_list.UserListAction
import com.mironenko.internship_socket_chat.ui.user_list.UserListState
import javax.inject.Inject

class LogoutInteractor @Inject constructor(
    private val chatRepository: ChatRepository
) : Interactor<UserListState, UserListAction> {

    override suspend fun invoke(state: UserListState, action: UserListAction): UserListAction {
        return if (action is UserListAction.Logout) {
            chatRepository.logout()
            UserListAction.LoggedOut
        } else {
            UserListAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserListAction): Boolean {
        return action is UserListAction.Logout
    }
}