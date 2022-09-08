package com.mironenko.internship_socket_chat.base

import kotlinx.coroutines.flow.Flow

interface Interactor<State, Action> {

    suspend fun invoke(state: State, action: Action): Action

    fun canHandle(action: Action): Boolean
}

interface SideEffectInteractor<State, Action> : Interactor<State, Action> {
    val sideEffectFlow: Flow<Action>
}