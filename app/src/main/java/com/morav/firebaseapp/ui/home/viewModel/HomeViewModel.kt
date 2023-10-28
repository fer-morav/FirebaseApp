package com.morav.firebaseapp.ui.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.morav.firebaseapp.domain.repository.auth.GetUserIdRepository
import com.morav.firebaseapp.domain.repository.auth.LogoutRepository

class HomeViewModel(
    private val getUserIdRepository: GetUserIdRepository,
    private val logoutRepository: LogoutRepository
) : ViewModel()  {

    private val _currentUser = MutableLiveData<String?>()
    val currentUser: LiveData<String?> = _currentUser

    private val _logout = MutableLiveData<Boolean?>()
    val logout: LiveData<Boolean?> = _logout

    fun getCurrentUser() {
        _currentUser.value = getUserIdRepository.getCurrentUserId()
    }

    fun logout() {
        _logout.value = logoutRepository.logout()
    }
}