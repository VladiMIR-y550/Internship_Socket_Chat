package com.mironenko.internship_socket_chat.data.interactor.user_list

import com.mironenko.internship_socket_chat.base.SideEffectInteractor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.user_list.UserListAction
import com.mironenko.internship_socket_chat.ui.user_list.UserListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersInteractor @Inject constructor(
    private val chatRepository: ChatRepository
) : SideEffectInteractor<UserListState, UserListAction> {

    override val sideEffectFlow: Flow<UserListAction>
        get() = chatRepository.users.map { users ->
            UserListAction.LoadedListUsers(users = users)
        }

    override suspend fun invoke(state: UserListState, action: UserListAction): UserListAction {
        return when (action) {
            is UserListAction.LoadUsers -> {
                chatRepository.downloadUsers()
                UserListAction.None
            }
            is UserListAction.Logout -> {
                chatRepository.logout()
                UserListAction.LoggedOut
            }
            else -> UserListAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserListAction): Boolean {
        return when (action) {
            is UserListAction.LoadUsers -> true
            is UserListAction.Logout -> true
            else -> false
        }
    }
}