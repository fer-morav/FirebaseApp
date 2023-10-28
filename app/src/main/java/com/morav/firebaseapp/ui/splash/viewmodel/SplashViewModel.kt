package com.morav.firebaseapp.ui.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.morav.firebaseapp.domain.repository.auth.GetUserIdRepository

class SplashViewModel(
    private val getUserIdRepository: GetUserIdRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<String?>()
    val currentUser: LiveData<String?> = _currentUser

    fun getCurrentUser() {
        _currentUser.value = getUserIdRepository.getCurrentUserId()
    }

}