package com.alian.schetime.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import com.alian.schetime.ui.base.viewmodels.factory.NoteViewModelFactory
import com.example.schetime.R
import com.example.schetime.databinding.ActivityHomeBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityHomeBinding

    override val kodein by kodein()

    private val factory: NoteViewModelFactory by instance()
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            binding.bottomNavigationBar,
            navHostFragment!!.navController
        )
    }
}