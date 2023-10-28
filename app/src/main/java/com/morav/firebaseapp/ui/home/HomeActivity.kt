package com.morav.firebaseapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.morav.firebaseapp.R
import com.morav.firebaseapp.databinding.ActivityHomeBinding
import com.morav.firebaseapp.domain.factory.ViewModelFactory
import com.morav.firebaseapp.ui.home.viewModel.HomeViewModel
import com.morav.firebaseapp.ui.login.LoginActivity
import com.morav.firebaseapp.util.changeNightMode
import com.morav.firebaseapp.util.isDark

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelFactory())[HomeViewModel::class.java]
        subscribeObserver()
        handleUI()
    }

    private fun subscribeObserver() {
        viewModel.logout.observe(this, Observer {
            val logoutState = it ?: return@Observer

            if (logoutState) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Log out Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun handleUI() = with(binding) {
        toolBarHome.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.switchMode) {
                delegate.changeNightMode(isDark())
            }
            true
        }
        logout.setOnClickListener {
            viewModel.logout()
        }
    }

}