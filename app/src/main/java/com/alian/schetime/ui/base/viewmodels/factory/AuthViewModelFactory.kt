package com.alian.schetime.ui.base.viewmodels.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.data.repository.AuthRepository
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import java.lang.IllegalArgumentException

class AuthViewModelFactory(
    private val app: Application,
    private val repository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(app, repository) as T
        }
        throw IllegalArgumentException("unknown class name")
    }
}