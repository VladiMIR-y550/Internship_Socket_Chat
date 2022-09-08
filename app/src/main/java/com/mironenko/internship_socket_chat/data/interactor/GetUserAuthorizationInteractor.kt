package com.mironenko.internship_socket_chat.data.interactor

import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.UserAuthorizationAction
import com.mironenko.internship_socket_chat.ui.UserAuthorizationState
import javax.inject.Inject

class GetUserAuthorizationInteractor @Inject constructor(
    private val repository: ChatRepository
) : Interactor<UserAuthorizationState, UserAuthorizationAction> {

    override suspend fun invoke(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationAction {
        return when (action) {
            is UserAuthorizationAction.SingIn -> {
                UserAuthorizationAction.LoggedIn(repository.userLogIn(action.userLogin))
            }
            else -> UserAuthorizationAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserAuthorizationAction): Boolean {
        return action is UserAuthorizationAction.SingIn
    }
}