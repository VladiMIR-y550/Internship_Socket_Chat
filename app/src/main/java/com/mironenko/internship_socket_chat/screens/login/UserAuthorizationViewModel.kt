package com.mironenko.internship_socket_chat.screens.login

import com.mironenko.internship_socket_chat.base.BaseViewModel
import com.mironenko.internship_socket_chat.data.interactor.GetUserAuthorizationInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAuthorizationViewModel @Inject constructor(
    interactors: Set<@JvmSuppressWildcards GetUserAuthorizationInteractor>,
) : BaseViewModel<UserAuthorizationState, UserAuthorizationAction>(
    interactors = interactors,
    reducer = UserAuthorizationReducer()
) {

    fun connectToServer() {
        action(UserAuthorizationAction.ConnectToServer)
    }

}