package com.mironenko.internship_socket_chat.base

interface Reducer<State, Action> {

    val initialState: State

    fun reduce(state: State, action: Action): State
}