package com.morav.firebaseapp.domain.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.morav.firebaseapp.data.datasource.AuthDataSource
import com.morav.firebaseapp.data.datasource.firebase.FirebaseAuthDataSource
import com.morav.firebaseapp.domain.repository.auth.GetUserIdRepository
import com.morav.firebaseapp.domain.repository.auth.LoginRepository
import com.morav.firebaseapp.domain.repository.auth.LogoutRepository
import com.morav.firebaseapp.domain.repository.auth.SignUpRepository
import com.morav.firebaseapp.ui.home.viewModel.HomeViewModel
import com.morav.firebaseapp.ui.login.viewmodel.LoginViewModel
import com.morav.firebaseapp.ui.signup.viewmodel.SignupViewModel
import com.morav.firebaseapp.ui.splash.viewmodel.SplashViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    private val dataSource: AuthDataSource = FirebaseAuthDataSource()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(
                getUserIdRepository = GetUserIdRepository(
                    dataSource = FirebaseAuthDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = FirebaseAuthDataSource()
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(
                signUpRepository = SignUpRepository(
                    dataSource = dataSource
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                getUserIdRepository = GetUserIdRepository(
                    dataSource = dataSource
                ),
                logoutRepository = LogoutRepository(
                    dataSource = dataSource
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}