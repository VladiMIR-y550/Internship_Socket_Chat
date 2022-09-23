package com.mironenko.internship_socket_chat.di.data

import android.content.Context
import android.content.SharedPreferences
import com.mironenko.internship_socket_chat.data.local.LocalDataSource
import com.mironenko.internship_socket_chat.data.local.Prefs
import com.mironenko.internship_socket_chat.data.repositories.UsersChatRepository
import com.mironenko.internship_socket_chat.data.socket.ChatSocket
import com.mironenko.internship_socket_chat.data.socket.ChatSocketClient
import com.mironenko.internship_socket_chat.util.PREFERENCE_FILE_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext applicationContext: Context): SharedPreferences {
        return applicationContext.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun providePrefs(sharedPreferences: SharedPreferences): Prefs {
        return Prefs(sharedPreferences = sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideChatSocketClient(): ChatSocketClient {
        return ChatSocketClient()
    }

    @Provides
    @Singleton
    fun provideRepository(
        chatSocket: ChatSocket,
        localStorage: LocalDataSource
    ): UsersChatRepository {
        return UsersChatRepository(chatSocket = chatSocket, localStorage = localStorage)
    }
}