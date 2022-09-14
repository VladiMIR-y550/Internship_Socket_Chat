package com.mironenko.internship_socket_chat.ui.user_list

import androidx.lifecycle.viewModelScope
import com.mironenko.internship_socket_chat.base.BaseViewModel
import com.mironenko.internship_socket_chat.base.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    interactors: Set<@JvmSuppressWildcards Interactor<UserListState, UserListAction>>
) : BaseViewModel<UserListState, UserListAction>(
    interactors = interactors,
    reducer = UserListReducer()
) {
    private var isUpdateUsers = true

    init {
        viewModelScope.launch {
            while (isUpdateUsers) {
                action(UserListAction.LoadUsers)
                delay(1000)
            }
        }
    }
}