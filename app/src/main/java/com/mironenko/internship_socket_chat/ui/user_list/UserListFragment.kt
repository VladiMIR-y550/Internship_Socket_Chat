package com.mironenko.internship_socket_chat.ui.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mironenko.internship_socket_chat.base.BaseFragment
import com.mironenko.internship_socket_chat.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>() {

    private val viewModel: UserListViewModel by viewModels()
    private val userAdapter = UserListAdapter()

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserListBinding =
        { inflater, container ->
            FragmentUserListBinding.inflate(inflater, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        viewModel.state.observe(viewLifecycleOwner) {
            binding.pbAuthProgress.isVisible = it.isLoading
            userAdapter.submitList(it.userList)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getUsers()
        }
    }
}