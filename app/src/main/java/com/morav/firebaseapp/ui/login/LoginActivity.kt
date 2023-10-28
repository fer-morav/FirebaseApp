package com.morav.firebaseapp.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.morav.firebaseapp.R
import com.morav.firebaseapp.databinding.ActivityLoginBinding
import com.morav.firebaseapp.domain.factory.ViewModelFactory
import com.morav.firebaseapp.ui.home.HomeActivity
import com.morav.firebaseapp.ui.login.viewmodel.LoginViewModel
import com.morav.firebaseapp.ui.signup.SignupActivity
import com.morav.firebaseapp.util.changeNightMode
import com.morav.firebaseapp.util.hideKeyboard
import com.morav.firebaseapp.util.isDark

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this, ViewModelFactory())[LoginViewModel::class.java]

        subscribeObserver()
        handleUI()
    }

    private fun subscribeObserver() = with(binding) {
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            btLogin.isEnabled = loginState.isDataValid

            email.error = if (loginState.emailError != null) {
                getString(loginState.emailError)
            } else {
                null
            }

            password.error = if (loginState.passwordError != null) {
                getString(loginState.passwordError)
            } else {
                null
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            hideProgressBar()
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
        })
    }

    private fun handleUI() = with(binding) {
        toolBarSignIn.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.switchMode) {
                delegate.changeNightMode(isDark())
            }
            true
        }
        etEmail.addTextChangedListener {
            loginViewModel.loginDataChanged(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
        etPassword.apply {
            addTextChangedListener {
                loginViewModel.loginDataChanged(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        hideKeyboard()
                    }
                }
                true
            }
        }
        btLogin.setOnClickListener {
            hideKeyboard()
            showProgressBar()
            loginViewModel.login(etEmail.text.toString(), etPassword.text.toString())
        }
        btSingUp.setOnClickListener {
            goToSignUp()
        }
    }

    private fun showProgressBar() = with(binding) {
        btLogin.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() = with(binding) {
        loading.visibility = View.GONE
        btLogin.visibility = View.VISIBLE
    }

    private fun updateUiWithUser(model: String) {
        Toast.makeText(
            applicationContext,
            "${getString(R.string.welcome)} $model",
            Toast.LENGTH_LONG
        ).show()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun goToSignUp() {
        startActivity(Intent(this, SignupActivity::class.java))
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
