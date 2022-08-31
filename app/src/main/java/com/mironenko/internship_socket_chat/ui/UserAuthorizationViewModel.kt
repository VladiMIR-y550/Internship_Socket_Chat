package com.mironenko.internship_socket_chat.ui

import androidx.lifecycle.viewModelScope
import com.mironenko.internship_socket_chat.base.BaseViewModel
import com.mironenko.internship_socket_chat.data.interactor.GetUserAuthorizationInteractor
import com.mironenko.internship_socket_chat.util.network.CheckNetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAuthorizationViewModel @Inject constructor(
    interactors: Set<@JvmSuppressWildcards GetUserAuthorizationInteractor>,
    networkStatus: CheckNetworkStatus
) : BaseViewModel<UserAuthorizationState, UserAuthorizationAction>(
    interactors = interactors,
    reducer = UserAuthorizationReducer(),
    networkStatus = networkStatus
) {

    init {
        viewModelScope.launch {
            networkStatus.getNetworkStatus.shareIn(viewModelScope, SharingStarted.Eagerly, 1)
                .collect {
                    action(UserAuthorizationAction.ConnectToServer(it))
                }
        }
    }
}