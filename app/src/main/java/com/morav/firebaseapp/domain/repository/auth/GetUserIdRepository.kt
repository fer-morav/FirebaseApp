package com.morav.firebaseapp.domain.repository.auth

import com.morav.firebaseapp.data.datasource.AuthDataSource

class GetUserIdRepository(private val dataSource: AuthDataSource) {

    fun getCurrentUserId(): String? {
        return dataSource.getCurrentUserId()
    }

}