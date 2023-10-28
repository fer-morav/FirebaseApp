package com.morav.firebaseapp.domain.repository.auth

import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.morav.firebaseapp.data.datasource.AuthDataSource
import com.morav.firebaseapp.util.Result

class SignUpRepository(val dataSource: AuthDataSource) {

    suspend fun singUp(email: String, password: String): Result<String> {
        return try {
            val result = dataSource.signUpEmail(email, password)
            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error(Exception("Error creating user"))
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.Error(Exception(e.message))
        }
    }
}