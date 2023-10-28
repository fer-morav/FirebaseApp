package com.morav.firebaseapp.domain.repository.auth

import com.google.firebase.auth.FirebaseAuthException
import com.morav.firebaseapp.data.datasource.AuthDataSource

class ResetPasswordRepository(private val dataSource: AuthDataSource) {

    suspend fun sendPasswordResetEmail(email: String): Boolean {
        return try {
            dataSource.sendPasswordResetEmail(email)
        } catch (e: FirebaseAuthException) {
            false
        }
    }

}