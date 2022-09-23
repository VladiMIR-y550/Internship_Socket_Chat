package com.mironenko.internship_socket_chat.di.data

import com.mironenko.internship_socket_chat.data.local.LocalDataSource
import com.mironenko.internship_socket_chat.data.local.Prefs
import com.mironenko.internship_socket_chat.data.repositories.UsersChatRepository
import com.mironenko.internship_socket_chat.data.socket.ChatSocket
import com.mironenko.internship_socket_chat.data.socket.ChatSocketClient
import com.mironenko.internship_socket_chat.domain.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryBindingsModule {

    @Binds
    fun bindsLocalDataSource(prefs: Prefs): LocalDataSource

    @Binds
    fun bindsChatSocket(chatSocketClient: ChatSocketClient): ChatSocket

    @Binds
    fun bindsChatRepositoryToUsersChatRepository(
        usersChatRepository: UsersChatRepository
    ): ChatRepository
}