package com.mironenko.internship_socket_chat.data.interactor

import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.UserAuthorizationAction
import com.mironenko.internship_socket_chat.ui.UserAuthorizationState
import com.mironenko.internship_socket_chat.util.network.InternetLostException
import com.mironenko.internship_socket_chat.util.network.NetworkStatus
import javax.inject.Inject

class GetUserAuthorizationInteractor @Inject constructor(
    private val repository: ChatRepository
) : Interactor<UserAuthorizationState, UserAuthorizationAction> {

    override suspend fun invoke(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationAction {
        return if (action is UserAuthorizationAction.ConnectToServer) {
            if (action.networkStatus == NetworkStatus.Status.AVAILABLE) {
                UserAuthorizationAction.ServerConnected(repository.connectToSocket())
            } else {
                UserAuthorizationAction.Error(InternetLostException("Internet connection problems"))
            }
        } else {
            UserAuthorizationAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserAuthorizationAction): Boolean {
        return action is UserAuthorizationAction.ConnectToServer
    }
}