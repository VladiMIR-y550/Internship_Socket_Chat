package com.mironenko.internship_socket_chat.ui

import com.mironenko.internship_socket_chat.base.BaseViewModel
import com.mironenko.internship_socket_chat.base.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAuthorizationViewModel @Inject constructor(
    interactors: Set<@JvmSuppressWildcards Interactor<UserAuthorizationState, UserAuthorizationAction>>,
) : BaseViewModel<UserAuthorizationState, UserAuthorizationAction>(
    interactors = interactors,
    reducer = UserAuthorizationReducer()
) {

    init {
        action(UserAuthorizationAction.ConnectToServer)
    }

    fun singIn(login: String) {
        action(UserAuthorizationAction.SingIn(login))
    }
}