package com.mironenko.internship_socket_chat.util.network

import kotlinx.coroutines.flow.Flow

interface CheckNetworkStatus {
    val getNetworkStatus: Flow<NetworkStatus.Status>
}