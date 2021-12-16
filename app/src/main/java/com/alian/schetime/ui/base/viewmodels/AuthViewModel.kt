package com.alian.schetime.ui.base.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alian.schetime.data.model.User
import com.alian.schetime.data.repository.AuthRepository
import com.alian.schetime.utils.Constants.Shared_Auth_Pref
import com.alian.schetime.utils.Constants.Shared_Only_Once_Pref
import com.alian.schetime.utils.Constants.Shared_User_Id
import com.alian.schetime.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(app: Application, private val repository: AuthRepository) :
    AndroidViewModel(app) {

    private var _signIn = MutableLiveData<Resource<User>>()
    val signIn = _signIn

    private var _signUp = MutableLiveData<Resource<Any>>()
    val signUp = _signUp

    private var _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn = _isLoggedIn

    private var _onlyOnce = MutableLiveData<Boolean>()
    val onlyOnce = _onlyOnce

    private val sharedPref: SharedPreferences = app.applicationContext.getSharedPreferences(
        Shared_Auth_Pref,
        Context.MODE_PRIVATE
    )

    init {
        getValueSplash()
        rememberMe()
    }

    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _signIn.value = Resource.Error("cannot be empty")
        } else {
            safeSignIn(email, password)
        }
    }

    private fun safeSignIn(email: String, password: String) {
        _signIn.postValue(Resource.Loading())
        if (email.isEmpty() || password.isEmpty()) {
            _signIn.postValue(Resource.Error("cannot be empty"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.signIn(email, password).also {
                    if (it.name != "") {
                        with(sharedPref.edit()) {
                            putInt(Shared_User_Id, it.id)
                            apply()
                        }
                        _signIn.postValue(Resource.SuccessWithoutData())
                    } else {
                        _signIn.postValue(Resource.Error("sign in failed"))
                    }
                }
            }
            _signIn.postValue(Resource.SuccessWithoutData())
        }
    }

    fun signUp(user: User) {
        safeSignUp(user)
    }

    private fun safeSignUp(user: User) {
        _signUp.postValue(Resource.Loading())
        if (user.name.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            _signUp.postValue(Resource.Error("cannot be empty"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.signUp(user)
            }
            _signUp.postValue(Resource.SuccessWithoutData())
        }
    }

    private fun rememberMe() {
        sharedPref.getInt(Shared_User_Id, 0).also {
            if (it != 0) {
                isLoggedIn.value = true
            }
        }
    }

    fun saveValueOnlyOnce() {
        with(sharedPref.edit()) {
            putBoolean(Shared_Only_Once_Pref, true)
            apply()
        }
    }

    private fun getValueSplash() {
        val value = sharedPref.getBoolean(Shared_Only_Once_Pref, false)
        if (value) {
            onlyOnce.value = value
        }
    }
}