package com.mironenko.internship_socket_chat.ui.user_list

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mironenko.internship_socket_chat.R
import com.mironenko.internship_socket_chat.base.BaseFragment
import com.mironenko.internship_socket_chat.databinding.FragmentUserListBinding
import com.mironenko.internship_socket_chat.domain.models.User
import com.mironenko.internship_socket_chat.domain.states.UserListState
import com.mironenko.internship_socket_chat.ui.chat.UserChatFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>(), MenuProvider {

    private val viewModel: UserListViewModel by viewModels()
    private val userAdapter by lazy {
        UserListAdapter { user ->
            navigateToChat(receiverId = user)
        }
    }

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserListBinding = {
            inflater, container ->
            FragmentUserListBinding.inflate(inflater, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.addMenuProvider(this)
        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        viewModel.state.observe(viewLifecycleOwner, ::render)

        lifecycleScope.launchWhenStarted {
            viewModel.getUsers()
        }
    }

    private fun render(newState: UserListState) {
        binding.pbAuthProgress.isVisible = newState.isLoading
        userAdapter.submitList(newState.userList)
        if (newState.isLoggedOut) {
            navigateToAuthorization()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.users_list_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_item_logout) {
            viewModel.logout()
        }
        return true
    }

    private fun navigateToChat(receiverId: User) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                UserChatFragment.newInstance(receiverId = receiverId.id)
            )
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToAuthorization() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.removeMenuProvider(this)
    }
}