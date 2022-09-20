package com.mironenko.internship_socket_chat.di

import com.mironenko.internship_socket_chat.data.interactor.local.LocalDataSource
import com.mironenko.internship_socket_chat.data.interactor.local.Prefs
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface LocalDataSourceBindingsModule {

    @Binds
    fun bindsLocalDataSourceToPrefs(
        prefs: Prefs
    ): LocalDataSource
}