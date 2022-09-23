package com.mironenko.internship_socket_chat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mironenko.internship_socket_chat.base.BaseFragment
import com.mironenko.internship_socket_chat.databinding.FragmentUserChatBinding
import com.mironenko.internship_socket_chat.domain.states.UserChatState
import com.mironenko.internship_socket_chat.util.ARG_RECEIVER_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserChatFragment : BaseFragment<FragmentUserChatBinding>() {

    private val viewModel: UserChatViewModel by viewModels()
    private val chatAdapter: UserChatAdapter by lazy {
        UserChatAdapter {
            return@UserChatAdapter viewModel.isSentMessage(it)
        }
    }

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserChatBinding = {
            inflater, container ->
            FragmentUserChatBinding.inflate(inflater, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::renderState)
        initViews()

        binding.etSendMessage.addTextChangedListener {
            viewModel.setMessage(it.toString())
        }

        binding.btnSend.setOnClickListener {
            viewModel.sendMessage()
            binding.etSendMessage.text.clear()
        }
    }

    private fun initViews() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true

        with(binding) {
            recyclerViewChat.adapter = chatAdapter
            recyclerViewChat.layoutManager = layoutManager
        }
    }

    private fun renderState(newState: UserChatState) {
        chatAdapter.submitList(newState.messages)
        binding.recyclerViewChat.smoothScrollToPosition(chatAdapter.itemCount)
    }

    companion object {
        fun newInstance(receiverId: String) = UserChatFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_RECEIVER_ID, receiverId)
            }
        }
    }
}