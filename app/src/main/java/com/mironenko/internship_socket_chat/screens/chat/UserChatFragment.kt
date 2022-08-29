package com.mironenko.internship_socket_chat.screens.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mironenko.internship_socket_chat.base.BaseFragment
import com.mironenko.internship_socket_chat.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint

class UserChatFragment : BaseFragment<FragmentUserListBinding>() {

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserListBinding = {
            inflater, container ->
            FragmentUserListBinding.inflate(inflater, container, false)
        }

}
