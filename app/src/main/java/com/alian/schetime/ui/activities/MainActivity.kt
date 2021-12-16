package com.alian.schetime.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import com.alian.schetime.ui.base.viewmodels.factory.AuthViewModelFactory
import com.example.schetime.databinding.ActivityMainBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityMainBinding

    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        binding.btnGetStarted.setOnClickListener {
            viewModel.saveValueOnlyOnce()
            Intent(this, WelcomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        viewModel.onlyOnce.observe(this, {
            Intent(this, WelcomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        })
    }
}