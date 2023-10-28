package com.morav.firebaseapp.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.morav.firebaseapp.R
import com.morav.firebaseapp.databinding.ActivitySignupBinding
import com.morav.firebaseapp.domain.factory.ViewModelFactory
import com.morav.firebaseapp.ui.home.HomeActivity
import com.morav.firebaseapp.ui.signup.viewmodel.SignupViewModel
import com.morav.firebaseapp.util.changeNightMode
import com.morav.firebaseapp.util.hideKeyboard
import com.morav.firebaseapp.util.isDark

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory())[SignupViewModel::class.java]
        subscribeObserver()
        handleUI()
    }

    private fun subscribeObserver() = with(binding)  {
        viewModel.signupFormState.observe(this@SignupActivity, Observer {
            val signupState = it ?: return@Observer
            btSignUp.isEnabled = signupState.isDataValid

            name.error = if (signupState.usernameError != null) {
                getString(signupState.usernameError)
            } else {
                null
            }

            email.error = if (signupState.emailError != null) {
                getString(signupState.emailError)
            } else {
                null
            }

            password.error = if (signupState.passwordError != null) {
                getString(signupState.passwordError)
            } else {
                null
            }
        })
        viewModel.signupResult.observe(this@SignupActivity, Observer {
            val signupResult = it ?: return@Observer

            hideProgressBar()
            if (signupResult.error != null) {
                showSignupFailed(signupResult.error)
            }
            if (signupResult.success != null) {
                updateUiWithUser(signupResult.success)
            }
        })
    }

    private fun handleUI() = with(binding) {
        toolBarSignUp.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.switchMode) {
                delegate.changeNightMode(isDark())
            }
            true
        }
        toolBarSignUp.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        etName.addTextChangedListener {
            viewModel.loginDataChanged(
                etName.text.toString().trim(),
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim()
            )
        }
        etEmail.addTextChangedListener {
            viewModel.loginDataChanged(
                etName.text.toString().trim(),
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim()
            )
        }
        etPassword.apply {
            addTextChangedListener {
                viewModel.loginDataChanged(
                    etName.text.toString().trim(),
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
        btSignUp.setOnClickListener {
            hideKeyboard()
            showProgressBar()
            viewModel.signup(etEmail.text.toString().trim(), etPassword.text.toString().trim())
        }
    }

    private fun showProgressBar() = with(binding) {
        btSignUp.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() = with(binding) {
        loading.visibility = View.GONE
        btSignUp.visibility = View.VISIBLE
    }

    private fun updateUiWithUser(model: String) {
        Toast.makeText(
            applicationContext,
            "${getString(R.string.welcome)} $model",
            Toast.LENGTH_LONG
        ).show()
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showSignupFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}