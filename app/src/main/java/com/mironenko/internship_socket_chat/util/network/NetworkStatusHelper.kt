package com.mironenko.internship_socket_chat.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject

class NetworkStatusHelper @Inject constructor(
    context: Context
) : CheckNetworkStatus {

//    init {
//        CoroutineScope(Dispatchers.Main).launch {
//            getNetworkStatusChat(context = context)
//        }
//    }

    override val getNetworkStatus: Flow<NetworkStatus.Status> =
        getNetworkStatusChat(context = context)


    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    private fun getNetworkStatusChat(context: Context): Flow<NetworkStatus.Status> {
        return callbackFlow {
//            Log.d("TAG","NetworkStatus Started")
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val callback = object : ConnectivityManager.NetworkCallback() {
                private var availabilityCheckJob: Job? = null

                override fun onUnavailable() {
                    availabilityCheckJob?.cancel()
//                    Log.d("TAG","NetworkStatus NetworkStatus.Status.UNAVAILABLE")
                    trySend(NetworkStatus.Status.UNAVAILABLE)
                }

                override fun onAvailable(network: Network) {
                    availabilityCheckJob = launch {
                        send(
                            if (checkAvailability()) {
//                                Log.d("TAG","NetworkStatus NetworkStatus.Status.AVAILABLE")
                                NetworkStatus.Status.AVAILABLE
                            } else {
//                                Log.d("TAG","NetworkStatus NetworkStatus.Status.AVAILABLE")
                                NetworkStatus.Status.UNAVAILABLE
                            }
                        )
                    }
                }

                override fun onLost(network: Network) {
                    availabilityCheckJob?.cancel()
//                    Log.d("TAG","NetworkStatus NetworkStatus.Status.UNAVAILABLE")
                    trySend(NetworkStatus.Status.UNAVAILABLE)
                }
            }

            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            connectivityManager.registerNetworkCallback(request, callback)
            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }.buffer(Channel.CONFLATED)     //удалить
    }

    private suspend fun checkAvailability(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                Socket().use {
                    it.connect(InetSocketAddress("8.8.8.8", 53))
                }
                true
            } catch (e: InternetLostException) {
                e.printStackTrace()
                false
            }
        }
    }
}