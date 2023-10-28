package com.morav.firebaseapp.data.datasource.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.morav.firebaseapp.data.datasource.AuthDataSource
import kotlinx.coroutines.tasks.await

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class FirebaseAuthDataSource : AuthDataSource {
    private val auth: FirebaseAuth = Firebase.auth

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    override suspend fun signUpEmail(
        email: String,
        password: String
    ): String? {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        return if (authResult.user == null) {
            null
        } else {
            authResult.user!!.uid
        }
    }

    override suspend fun login(email: String, password: String): String? {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        return if (authResult.user == null) {
            null
        } else {
            authResult.user!!.uid
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Boolean {
        auth.sendPasswordResetEmail(email).await()
        return true
    }

    override fun logout(): Boolean {
        return if (auth.currentUser != null) {
            auth.signOut()
            true
        } else{
            false
        }
    }
}