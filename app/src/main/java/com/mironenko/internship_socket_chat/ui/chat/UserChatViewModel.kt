package com.mironenko.internship_socket_chat.ui.chat

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.mironenko.internship_socket_chat.base.BaseViewModel
import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.util.ARG_RECEIVER_ID
import com.mironenko.internship_socket_chat.util.SAVED_USER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserChatViewModel @Inject constructor(
    interactors: Set<@JvmSuppressWildcards Interactor<UserChatState, UserChatAction>>,
    savedStateHandle: SavedStateHandle,
    sharedPreferences: SharedPreferences
) : BaseViewModel<UserChatState, UserChatAction>(
    interactors = interactors,
    reducer = UserChatReducer(
        sharedPreferences.getString(SAVED_USER_ID, "") ?: "",
        savedStateHandle.get<String>(ARG_RECEIVER_ID)!!
    )
) {

    fun setMessage(message: String) {
        action(UserChatAction.SetMessage(message))
    }

    fun sendMessage() {
        action(UserChatAction.SendMessage)
    }
}