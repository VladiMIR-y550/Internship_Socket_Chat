package com.mironenko.internship_socket_chat.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mironenko.internship_socket_chat.base.BaseFragment
import com.mironenko.internship_socket_chat.databinding.FragmentUserAuthorizationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAuthorizationFragment : BaseFragment<FragmentUserAuthorizationBinding>() {

    private val viewModel: UserAuthorizationViewModel by viewModels()

    override val viewBindingProvider: (
        LayoutInflater, ViewGroup?
    ) -> FragmentUserAuthorizationBinding = { inflater, container ->
        FragmentUserAuthorizationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.connectToServer()
        }

        binding.btnSingIn.setOnClickListener {

        }
    }
}