package com.mironenko.internship_socket_chat.di

import com.mironenko.internship_socket_chat.util.network.CheckNetworkStatus
import com.mironenko.internship_socket_chat.util.network.NetworkStatusHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface NetworkStatusBindingsModule {

    @Binds
    fun bindCheckNetworkStatusToNetworkStatusHelper(
        networkStatusHelper: NetworkStatusHelper
    ): CheckNetworkStatus
}