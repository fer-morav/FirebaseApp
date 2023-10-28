package com.morav.firebaseapp.data.model

/**
 * Data validation state of the login form.
 */
data class SignupFormState(
    val usernameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
