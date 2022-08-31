package com.mironenko.internship_socket_chat.di

import android.content.Context
import com.mironenko.internship_socket_chat.util.network.NetworkStatusHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
class NetworkStatusHelperModule {

    @Provides
    fun provideNetworkStatusHelper(@ApplicationContext context: Context): NetworkStatusHelper {
        return NetworkStatusHelper(context = context)
    }
}