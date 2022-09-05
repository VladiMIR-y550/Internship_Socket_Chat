package com.mironenko.internship_socket_chat.data.interactor

import android.util.Log
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
                Log.d("TAG", "Action.Authorized")
                UserAuthorizationAction.Authorized
            } else {
                Log.d("TAG", "Action.UnAuthorized")
                UserAuthorizationAction.UnAuthorized
            }
        }

    override suspend fun invoke(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationAction {
       return when (action) {
           UserAuthorizationAction.UnAuthorized -> {
               UserAuthorizationAction.Authorization
           }
            is UserAuthorizationAction.NetworkAvailable -> {
                UserAuthorizationAction.ConnectToServer
            }
            is UserAuthorizationAction.NetworkUnavailable -> {
                UserAuthorizationAction.DisconnectServer
            }
            else -> UserAuthorizationAction.Error(
                IllegalArgumentException("Wrong Action $action")
            )
        }
    }

    override fun canHandle(action: UserAuthorizationAction): Boolean {
        return when (action) {
            is UserAuthorizationAction.NetworkAvailable -> true
            is UserAuthorizationAction.NetworkUnavailable -> true
            is UserAuthorizationAction.UnAuthorized -> true
            else -> false
        }
    }
}