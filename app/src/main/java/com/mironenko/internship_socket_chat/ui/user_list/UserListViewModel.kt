package com.mironenko.internship_socket_chat.ui.user_list

import com.mironenko.internship_socket_chat.base.BaseViewModel
import com.mironenko.internship_socket_chat.base.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    interactors: Set<@JvmSuppressWildcards Interactor<UserListState, UserListAction>>
) : BaseViewModel<UserListState, UserListAction>(
    interactors = interactors,
    reducer = UserListReducer()
) {
    init {
        action(UserListAction.LoadUsers)
    }
}