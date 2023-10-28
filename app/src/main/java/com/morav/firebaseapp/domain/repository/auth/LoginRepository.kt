package com.morav.firebaseapp.domain.repository.auth

import com.google.firebase.auth.FirebaseAuthException
import com.morav.firebaseapp.data.datasource.AuthDataSource
import com.morav.firebaseapp.util.Result

class LoginRepository(private val dataSource: AuthDataSource) {

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = dataSource.login(email, password)
            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error(Exception("Error logging in"))
            }
        } catch (e: FirebaseAuthException) {
            Result.Error(Exception(e.message))
        }
    }

}