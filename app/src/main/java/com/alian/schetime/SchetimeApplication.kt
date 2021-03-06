package com.alian.schetime

import android.app.Application
import com.alian.schetime.data.db.ScheTimeDB
import com.alian.schetime.data.repository.AuthRepository
import com.alian.schetime.data.repository.NoteRepository
import com.alian.schetime.data.repository.UserRepository
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import com.alian.schetime.ui.base.viewmodels.factory.AuthViewModelFactory
import com.alian.schetime.ui.base.viewmodels.factory.NoteViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class SchetimeApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@SchetimeApplication))
        bind() from singleton { ScheTimeDB(instance()) }
        bind() from singleton { AuthRepository(instance()) }
        bind() from singleton { NoteRepository(instance()) }
        bind() from singleton { UserRepository(instance()) }
        bind() from singleton { AuthViewModel(instance(), instance()) }
        bind() from singleton { NoteViewModel(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance(), instance()) }
        bind() from provider { NoteViewModelFactory(instance(), instance(), instance()) }
    }
}