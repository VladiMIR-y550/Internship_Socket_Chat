package com.mironenko.internship_socket_chat.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.mironenko.internship_socket_chat.R
import com.mironenko.internship_socket_chat.base.BaseFragment
import com.mironenko.internship_socket_chat.databinding.FragmentUserAuthorizationBinding
import com.mironenko.internship_socket_chat.ui.user_list.UserListFragment
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

        viewModel.state.observe(viewLifecycleOwner) {
            binding.tvAuthStatus.text = it.authStatus
            binding.tvInput.text = it.message
            binding.tvInternetConnection.isVisible = it.isInternetAvailable
            binding.pbAuthProgress.isVisible = it.isProgress
            if (it.isAuth) {
                navigationToUserListScreen()
            }
        }
        binding.etLoginInput.addTextChangedListener {
            viewModel.setLogin(it.toString())
        }

        binding.btnSingIn.setOnClickListener {
            viewModel.singIn()
        }
    }

    private fun navigationToUserListScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UserListFragment())
            .addToBackStack(null)
            .commit()
    }
}