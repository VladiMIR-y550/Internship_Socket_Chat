package com.mironenko.internship_socket_chat.di

import com.mironenko.internship_socket_chat.data.socket.ChatSocket
import com.mironenko.internship_socket_chat.data.socket.ChatSocketClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface ChatSocketBindingsModule {

    @Binds
    fun bindUserChatSocket(ChatSocketClient: ChatSocketClient): ChatSocket
}