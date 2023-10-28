package com.morav.firebaseapp.ui.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morav.firebaseapp.R
import com.morav.firebaseapp.domain.repository.auth.SignUpRepository
import com.morav.firebaseapp.data.model.SignResult
import com.morav.firebaseapp.data.model.SignupFormState
import com.morav.firebaseapp.util.Result
import com.morav.firebaseapp.util.isValidEmail
import com.morav.firebaseapp.util.isValidPassword
import kotlinx.coroutines.launch

class SignupViewModel (
    private val  signUpRepository: SignUpRepository
): ViewModel() {

    private val _signupResult = MutableLiveData<SignResult>()
    val signupResult: LiveData<SignResult> = _signupResult

    private val _signupForm = MutableLiveData<SignupFormState>()
    val signupFormState: LiveData<SignupFormState> = _signupForm

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            val result = signUpRepository.singUp(email, password)

            if (result is Result.Success) {
                _signupResult.value =
                    SignResult(
                        success = result.data,
                        error = null
                    )
            } else if (result is Result.Error) {
                _signupResult.value = SignResult(
                    error = result.exception.message,
                    success = null
                )
            }
        }
    }

    fun loginDataChanged(username: String, email: String, password: String) {
        if (!isNameValid(username)) {
            _signupForm.value = SignupFormState(usernameError = R.string.invalid_username)
        } else if (!isUserNameValid(email)) {
            _signupForm.value = SignupFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _signupForm.value = SignupFormState(passwordError = R.string.invalid_password)
        } else {
            _signupForm.value = SignupFormState(isDataValid = true)
        }
    }

    private fun isNameValid(name: String): Boolean = name.isNotEmpty()

    private fun isUserNameValid(email: String): Boolean = email.isValidEmail()

    private fun isPasswordValid(password: String): Boolean = password.isValidPassword()
}