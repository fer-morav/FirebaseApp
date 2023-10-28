package com.morav.firebaseapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.morav.firebaseapp.domain.factory.ViewModelFactory
import com.morav.firebaseapp.ui.home.HomeActivity
import com.morav.firebaseapp.ui.login.LoginActivity
import com.morav.firebaseapp.ui.splash.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel = ViewModelProvider(this, ViewModelFactory())[SplashViewModel::class.java]
        subscribeObserver()
        splashViewModel.getCurrentUser()
    }

    private fun subscribeObserver() {
        splashViewModel.currentUser.observe(this@SplashActivity) { userId ->
            if (userId == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }
}