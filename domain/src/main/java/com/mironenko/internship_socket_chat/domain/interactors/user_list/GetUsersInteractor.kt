package com.mironenko.internship_socket_chat.domain.interactors.user_list

import com.mironenko.internship_socket_chat.domain.ChatRepository
import com.mironenko.internship_socket_chat.domain.actions.UserListAction
import com.mironenko.internship_socket_chat.domain.base.SideEffectInteractor
import com.mironenko.internship_socket_chat.domain.states.UserListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersInteractor(
    private val chatRepository: ChatRepository
) : SideEffectInteractor<UserListState, UserListAction> {

    override val sideEffectFlow: Flow<UserListAction>
        get() = chatRepository.users.map { users ->
            UserListAction.LoadedListUsers(users = users)
        }

    override suspend fun invoke(state: UserListState, action: UserListAction): UserListAction {
        return if (action is UserListAction.LoadUsers) {
            chatRepository.downloadUsers()
            UserListAction.None
        } else {
            UserListAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserListAction): Boolean {
        return action is UserListAction.LoadUsers
    }
}