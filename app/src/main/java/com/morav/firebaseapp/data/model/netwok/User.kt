package com.morav.firebaseapp.data.model.netwok

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val userId: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    val providerId: String,
    val phoneNumber: String
)