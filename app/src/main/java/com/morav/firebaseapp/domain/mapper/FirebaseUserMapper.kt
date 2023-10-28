package com.morav.firebaseapp.domain.mapper

import com.google.firebase.auth.FirebaseUser
import com.morav.firebaseapp.data.model.netwok.User

class FirebaseUserMapper {

    fun mapFirebaseUserToUser(user: FirebaseUser) : User {
        return User(
            userId = user.uid,
            name = user.displayName ?: "",
            email = user.email ?: "",
            photoUrl = user.photoUrl?.path ?: "",
            providerId = user.providerId,
            phoneNumber = user.phoneNumber ?: ""
        )
    }
}