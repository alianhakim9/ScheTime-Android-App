package com.alian.schetime.ui.base.viewmodels.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.data.repository.NoteRepository
import com.alian.schetime.data.repository.UserRepository
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import java.lang.IllegalArgumentException

class NoteViewModelFactory(
    private val app: Application,
    private val repository: NoteRepository,
    private val userRepository: UserRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(app, repository, userRepository) as T
        }
        throw IllegalArgumentException("unknown class name")
    }
}