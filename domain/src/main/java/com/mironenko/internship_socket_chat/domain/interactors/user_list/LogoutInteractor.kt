package com.mironenko.internship_socket_chat.domain.interactors.user_list

import com.mironenko.internship_socket_chat.domain.ChatRepository
import com.mironenko.internship_socket_chat.domain.actions.UserListAction
import com.mironenko.internship_socket_chat.domain.base.Interactor
import com.mironenko.internship_socket_chat.domain.states.UserListState

class LogoutInteractor(
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