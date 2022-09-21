package com.mironenko.internship_socket_chat.data.local

interface LocalDataSource {
    var loginPref: String
    fun clearPref()
}