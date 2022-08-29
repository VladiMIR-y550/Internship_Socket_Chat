package com.mironenko.internship_socket_chat.base

interface Interactor<State, Action> {

    suspend fun invoke(state: State, action: Action): Action

    fun canHandle(action: Action): Boolean
}