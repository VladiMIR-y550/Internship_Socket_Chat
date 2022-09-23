package com.mironenko.internship_socket_chat.di.domain

import com.mironenko.internship_socket_chat.domain.ChatRepository
import com.mironenko.internship_socket_chat.domain.interactors.auth.AuthStatusInteractor
import com.mironenko.internship_socket_chat.domain.interactors.auth.GetUserAuthorizationInteractor
import com.mironenko.internship_socket_chat.domain.interactors.chat.GetNewMessageInteractor
import com.mironenko.internship_socket_chat.domain.interactors.chat.SendMessageInteractor
import com.mironenko.internship_socket_chat.domain.interactors.user_list.GetUsersInteractor
import com.mironenko.internship_socket_chat.domain.interactors.user_list.LogoutInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class DomainModule {

    @Provides
    fun provideAuthStatusInteractor(chatRepository: ChatRepository): AuthStatusInteractor {
        return AuthStatusInteractor(chatRepository = chatRepository)
    }

    @Provides
    fun provideGetUserAuthorizationInteractor(chatRepository: ChatRepository): GetUserAuthorizationInteractor {
        return GetUserAuthorizationInteractor(chatRepository = chatRepository)
    }

    @Provides
    fun provideGetNewMessageInteractor(chatRepository: ChatRepository): GetNewMessageInteractor {
        return GetNewMessageInteractor(chatRepository = chatRepository)
    }

    @Provides
    fun provideSendMessageInteractor(chatRepository: ChatRepository): SendMessageInteractor {
        return SendMessageInteractor(chatRepository = chatRepository)
    }

    @Provides
    fun provideGetUsersInteractor(chatRepository: ChatRepository): GetUsersInteractor {
        return GetUsersInteractor(chatRepository = chatRepository)
    }

    @Provides
    fun provideLogoutInteractor(chatRepository: ChatRepository): LogoutInteractor {
        return LogoutInteractor(chatRepository = chatRepository)
    }
}