package com.mironenko.internship_socket_chat.di.domain

import com.mironenko.internship_socket_chat.domain.base.Interactor
import com.mironenko.internship_socket_chat.domain.interactors.auth.AuthStatusInteractor
import com.mironenko.internship_socket_chat.domain.interactors.auth.GetUserAuthorizationInteractor
import com.mironenko.internship_socket_chat.domain.interactors.chat.GetNewMessageInteractor
import com.mironenko.internship_socket_chat.domain.interactors.chat.SendMessageInteractor
import com.mironenko.internship_socket_chat.domain.interactors.user_list.GetUsersInteractor
import com.mironenko.internship_socket_chat.domain.interactors.user_list.LogoutInteractor
import com.mironenko.internship_socket_chat.domain.actions.UserAuthorizationAction
import com.mironenko.internship_socket_chat.domain.states.UserAuthorizationState
import com.mironenko.internship_socket_chat.domain.actions.UserChatAction
import com.mironenko.internship_socket_chat.domain.states.UserChatState
import com.mironenko.internship_socket_chat.domain.actions.UserListAction
import com.mironenko.internship_socket_chat.domain.states.UserListState
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