package com.morav.firebaseapp.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morav.firebaseapp.util.Result
import com.morav.firebaseapp.R
import com.morav.firebaseapp.domain.repository.auth.LoginRepository
import com.morav.firebaseapp.data.model.LoginFormState
import com.morav.firebaseapp.data.model.SignResult
import com.morav.firebaseapp.util.isValidEmail
import com.morav.firebaseapp.util.isValidPassword
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<SignResult>()
    val loginResult: LiveData<SignResult> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(email, password)

            if (result is Result.Success) {
                _loginResult.value =
                    SignResult(
                        success = result.data,
                        error = null
                    )
            } else if (result is Result.Error) {
                _loginResult.value = SignResult(
                    error = result.exception.message,
                    success = null
                )
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(email: String): Boolean = email.isValidEmail()

    private fun isPasswordValid(password: String): Boolean = password.isValidPassword()
}