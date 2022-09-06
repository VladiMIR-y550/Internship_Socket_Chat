package com.mironenko.internship_socket_chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mironenko.internship_socket_chat.databinding.ActivityMainBinding
import com.mironenko.internship_socket_chat.ui.UserAuthorizationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var bindingInternal: ActivityMainBinding? = null
    private val binding get() = bindingInternal!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingInternal = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, UserAuthorizationFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingInternal = null
    }
}