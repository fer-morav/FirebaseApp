package com.morav.firebaseapp.domain.repository.auth

import com.morav.firebaseapp.data.datasource.AuthDataSource

class LogoutRepository(private val dataSource: AuthDataSource) {

    fun logout(): Boolean {
        return try {
            dataSource.logout()
        } catch (e: Exception) {
            false
        }
    }

}