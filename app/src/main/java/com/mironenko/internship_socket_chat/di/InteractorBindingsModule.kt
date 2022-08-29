package com.mironenko.internship_socket_chat.di

import com.mironenko.internship_socket_chat.data.interactor.GetUserAuthorizationInteractor
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
    ): GetUserAuthorizationInteractor

}