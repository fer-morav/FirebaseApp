package com.morav.firebaseapp.data.datasource

interface AuthDataSource {
    fun getCurrentUserId(): String?
    suspend fun signUpEmail(email: String, password: String): String?
    suspend fun login(email: String, password: String): String?
    suspend fun sendPasswordResetEmail(email: String): Boolean
    fun logout(): Boolean
}