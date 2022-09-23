package com.mironenko.internship_socket_chat.ui.user_list

import com.mironenko.internship_socket_chat.base.BaseViewModel
import com.mironenko.internship_socket_chat.domain.actions.UserListAction
import com.mironenko.internship_socket_chat.domain.base.Interactor
import com.mironenko.internship_socket_chat.domain.reducers.UserListReducer
import com.mironenko.internship_socket_chat.domain.states.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    interactors: Set<@JvmSuppressWildcards Interactor<UserListState, UserListAction>>
) : BaseViewModel<UserListState, UserListAction>(
    interactors = interactors,
    reducer = UserListReducer()
) {

    suspend fun getUsers() {
        withContext(Dispatchers.Main) {
            while (true) {
                action(UserListAction.LoadUsers)
                delay(1000)
            }
        }
    }

    fun logout() {
        action(UserListAction.Logout)
    }
}