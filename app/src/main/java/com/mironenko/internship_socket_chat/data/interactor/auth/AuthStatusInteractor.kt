package com.mironenko.internship_socket_chat.data.interactor.auth

import com.mironenko.internship_socket_chat.base.SideEffectInteractor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.auth.UserAuthorizationAction
import com.mironenko.internship_socket_chat.ui.auth.UserAuthorizationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthStatusInteractor @Inject constructor(
    private val chatRepository: ChatRepository
) : SideEffectInteractor<UserAuthorizationState, UserAuthorizationAction> {

    override val sideEffectFlow: Flow<UserAuthorizationAction>
        get() = chatRepository.isAuthorized.map {
            if (it) {
                UserAuthorizationAction.Authorized
            } else {
                UserAuthorizationAction.UnAuthorized
            }
        }

    override suspend fun invoke(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationAction {
        throw IllegalArgumentException("Wrong Action $action")
    }

    override fun canHandle(action: UserAuthorizationAction): Boolean {
        return false
    }
}
