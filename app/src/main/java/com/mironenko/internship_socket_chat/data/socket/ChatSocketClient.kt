package com.mironenko.internship_socket_chat.data.socket

import android.util.Log
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketAddress
import java.net.SocketException
import javax.inject.Inject

const val SOCKET_PORT_UDP = 8888
const val SOCKET_HOST = "255.255.255.255"

class ChatSocketClient @Inject constructor() : ChatSocket {
    private var socketAddress: SocketAddress? = null

    override fun getSocketAddressByUdp(){
        try {
            val datagramSocket = DatagramSocket()
            val inetAddress = InetAddress.getByName(SOCKET_HOST)

            datagramSocket.connect(inetAddress, SOCKET_PORT_UDP)

            socketAddress = datagramSocket.localSocketAddress
            Log.d("TAG", "socketAddress = $socketAddress")

        } catch (e: SocketException) {
            e.printStackTrace()
        }
    }

}
