package com.mironenko.internship_socket_chat.data.interactor

import com.mironenko.internship_socket_chat.base.SideEffectInteractor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.UserAuthorizationAction
import com.mironenko.internship_socket_chat.ui.UserAuthorizationState
import com.mironenko.internship_socket_chat.util.network.CheckNetworkStatus
import com.mironenko.internship_socket_chat.util.network.InternetLostException
import com.mironenko.internship_socket_chat.util.network.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkStatusInteractor @Inject constructor(
    private val repository: ChatRepository,
    private val networkStatus: CheckNetworkStatus
) : SideEffectInteractor<UserAuthorizationState, UserAuthorizationAction> {

    private val _networkStatus =
        MutableStateFlow(NetworkStatus.Status.UNAVAILABLE)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            networkStatus.getNetworkStatus.collect {
                _networkStatus.emit(it)
            }
        }
    }

    override val sideEffectFlow: Flow<UserAuthorizationAction>
        get() = _networkStatus.map {
            if (it == NetworkStatus.Status.UNAVAILABLE) {
                UserAuthorizationAction.NetworkUnavailable(false)
            } else {
                UserAuthorizationAction.NetworkAvailable(true)
            }
        }

    override suspend fun invoke(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationAction {
        return when (action) {
            UserAuthorizationAction.ConnectToServer -> {
                if (state.isInternetAvailable) {
                    UserAuthorizationAction.ServerStatus(repository.connectToServer())
                } else {
                    UserAuthorizationAction.Error(InternetLostException("Internet lost"))
                }
            }
            UserAuthorizationAction.DisconnectServer -> {
                repository.disconnectServer()
                UserAuthorizationAction.None
            }
            else -> UserAuthorizationAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserAuthorizationAction): Boolean {
        return when (action) {
            is UserAuthorizationAction.ConnectToServer -> true
            is UserAuthorizationAction.DisconnectServer -> true
            else -> false
        }
    }
}