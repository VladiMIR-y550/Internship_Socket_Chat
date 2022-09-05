package com.mironenko.internship_socket_chat.ui

import com.mironenko.internship_socket_chat.base.BaseViewModel
import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.util.network.CheckNetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAuthorizationViewModel @Inject constructor(
    interactors: Set<@JvmSuppressWildcards Interactor<UserAuthorizationState, UserAuthorizationAction>>,
    networkStatus: CheckNetworkStatus
) : BaseViewModel<UserAuthorizationState, UserAuthorizationAction>(
    interactors = interactors,
    reducer = UserAuthorizationReducer(),
    networkStatus = networkStatus
) {

    fun authorization() {
        action(UserAuthorizationAction.Authorization)
    }
}