package com.mironenko.internship_socket_chat.di

import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.data.interactor.AuthStatusInteractor
import com.mironenko.internship_socket_chat.data.interactor.GetUserAuthorizationInteractor
import com.mironenko.internship_socket_chat.data.interactor.NetworkStatusInteractor
import com.mironenko.internship_socket_chat.ui.UserAuthorizationAction
import com.mironenko.internship_socket_chat.ui.UserAuthorizationState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds

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
    fun bindNetworkStatusInterceptor(
        networkStatusInteractor: NetworkStatusInteractor
    ): Interactor<UserAuthorizationState, UserAuthorizationAction>
}