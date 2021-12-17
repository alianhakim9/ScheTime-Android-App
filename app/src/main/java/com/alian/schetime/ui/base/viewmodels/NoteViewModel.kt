package com.alian.schetime.ui.base.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alian.schetime.data.model.Note
import com.alian.schetime.data.model.User
import com.alian.schetime.data.repository.NoteRepository
import com.alian.schetime.data.repository.UserRepository
import com.alian.schetime.utils.Constants
import com.alian.schetime.utils.Constants.Shared_User_Id_Pref
import com.alian.schetime.utils.Constants.Shared_User_Pref
import com.alian.schetime.utils.Resource
import com.alian.schetime.utils.isValidEmail
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val repository: NoteRepository,
    private val userRepository: UserRepository,
) :
    AndroidViewModel(app) {

    private var _notes = MutableLiveData<Resource<List<Note>>>()
    val notes: LiveData<Resource<List<Note>>> = _notes

    private var _addNote = MutableLiveData<Resource<Any>>()
    val addNote: LiveData<Resource<Any>> = _addNote

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var _updateProfile = MutableLiveData<Resource<Any>>()
    val updateProfile: LiveData<Resource<Any>> = _updateProfile

    var noteTime = MutableLiveData<String>()

    private val sharedPref: SharedPreferences = app.applicationContext.getSharedPreferences(
        Constants.Shared_Auth_Pref,
        Context.MODE_PRIVATE
    )

    fun loadNotes() {
        _notes.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            repository.get(sharedPref.getInt(Shared_User_Id_Pref, 0)).also {
                _notes.postValue(Resource.Success(it))
            }
        }
    }

    fun addNote(title: String, description: String) {
        val userId = sharedPref.getInt(Shared_User_Id_Pref, 0)
        val newNote = Note(0, title, description, noteTime.value.toString(), userId)
        _addNote.postValue(Resource.Loading())
        if (newNote.title.isEmpty() || newNote.text.isEmpty()) {
            _addNote.postValue(Resource.Error("cannot be empty"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.add(newNote)
            }
            _addNote.postValue(Resource.SuccessWithoutData())
        }
    }

    fun userProfile() {
        val gson = Gson()
        val json = sharedPref.getString(Shared_User_Pref, "")
        val user: User = gson.fromJson(json, User::class.java)
        _user.postValue(user)
    }

    fun updateProfile(user: User) {
        if (user.name.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            _updateProfile.postValue(Resource.Error("can't be empty"))
        } else if (!user.email.isValidEmail()) {
            _updateProfile.postValue(Resource.Error("email invalid"))
        } else if (user.password.length < 8) {
            _updateProfile.postValue(Resource.Error("password minimum 8 character"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.updateProfile(user)
            }

            with(sharedPref.edit()) {
                remove(Shared_User_Pref)
                val gson = Gson()
                val json = gson.toJson(user)
                putString(Shared_User_Pref, json)
                apply()
            }
            _updateProfile.postValue(Resource.SuccessWithoutData())
        }
    }

    fun logout() {
        with(sharedPref.edit()) {
            remove(Shared_User_Id_Pref)
            remove(Shared_User_Pref)
            apply()
        }
    }


}