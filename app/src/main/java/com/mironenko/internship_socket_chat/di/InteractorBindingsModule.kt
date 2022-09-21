package com.mironenko.internship_socket_chat.di

import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.data.interactor.auth.AuthStatusInteractor
import com.mironenko.internship_socket_chat.data.interactor.auth.GetUserAuthorizationInteractor
import com.mironenko.internship_socket_chat.data.interactor.chat.GetNewMessageInteractor
import com.mironenko.internship_socket_chat.data.interactor.chat.SendMessageInteractor
import com.mironenko.internship_socket_chat.data.interactor.user_list.GetUsersInteractor
import com.mironenko.internship_socket_chat.data.interactor.user_list.LogoutInteractor
import com.mironenko.internship_socket_chat.ui.auth.UserAuthorizationAction
import com.mironenko.internship_socket_chat.ui.auth.UserAuthorizationState
import com.mironenko.internship_socket_chat.ui.chat.UserChatAction
import com.mironenko.internship_socket_chat.ui.chat.UserChatState
import com.mironenko.internship_socket_chat.ui.user_list.UserListAction
import com.mironenko.internship_socket_chat.ui.user_list.UserListState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoSet

@InstallIn(ViewModelComponent::class)
@Module
interface InteractorBindingsModule {

    @Binds
    @IntoSet
    fun bindGetUserAuthorizationInterceptor(
        getUserAuthorizationInteractor: GetUserAuthorizationInteractor
    ): Interactor<UserAuthorizationState, UserAuthorizationAction>

    @Binds
    @IntoSet
    fun bindAuthStatusInterceptor(
        authStatusInteractor: AuthStatusInteractor
    ): Interactor<UserAuthorizationState, UserAuthorizationAction>

    @Binds
    @IntoSet
    fun bindUserListInteractor(
        getUsersInteractor: GetUsersInteractor
    ): Interactor<UserListState, UserListAction>

    @Binds
    @IntoSet
    fun bindLogoutInteractor(
        logoutInteractor: LogoutInteractor
    ): Interactor<UserListState, UserListAction>

    @Binds
    @IntoSet
    fun bindUserChatInteractor(
        sendMessageInteractor: SendMessageInteractor
    ): Interactor<UserChatState, UserChatAction>

    @Binds
    @IntoSet
    fun bindGetNewMessageInteractor(
        getNewMessageInteractor: GetNewMessageInteractor
    ): Interactor<UserChatState, UserChatAction>
}