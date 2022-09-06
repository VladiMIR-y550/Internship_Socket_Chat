package com.mironenko.internship_socket_chat.di

import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.data.UsersChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryBindingsModule {

    @Binds
    fun bindsChatRepositoryToUsersChatRepository(
        usersChatRepository: UsersChatRepository
    ): ChatRepository
}