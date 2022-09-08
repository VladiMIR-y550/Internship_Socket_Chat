package com.mironenko.internship_socket_chat.data.interactor

import com.mironenko.internship_socket_chat.base.SideEffectInteractor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.UserAuthorizationAction
import com.mironenko.internship_socket_chat.ui.UserAuthorizationState
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
        return when (action) {
            UserAuthorizationAction.ConnectToServer -> {
                try {
                    UserAuthorizationAction.ServerStatus(chatRepository.connectToServer())
                } catch (e: Exception) {
                    UserAuthorizationAction.Error(e)
                }
            }
            UserAuthorizationAction.UnAuthorized -> {
                UserAuthorizationAction.Error(
                    IllegalArgumentException("Authorization is Fall")
                )
            }
            else -> UserAuthorizationAction.Error(
                IllegalArgumentException("Wrong Action $action")
            )
        }
    }

    override fun canHandle(action: UserAuthorizationAction): Boolean {
        return when (action) {
            is UserAuthorizationAction.ConnectToServer -> true
            is UserAuthorizationAction.UnAuthorized -> true
            else -> false
        }
    }
}