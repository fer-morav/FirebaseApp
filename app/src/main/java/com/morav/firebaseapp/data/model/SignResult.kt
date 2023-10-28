package com.morav.firebaseapp.data.model

/**
 * Authentication result : success (user details) or error message.
 */
data class SignResult(
    val success: String? = null,
    val error: String? = null
)