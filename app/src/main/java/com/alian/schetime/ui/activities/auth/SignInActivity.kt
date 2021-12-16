package com.alian.schetime.ui.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.ui.activities.HomeActivity
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import com.alian.schetime.ui.base.viewmodels.factory.AuthViewModelFactory
import com.example.schetime.R
import com.example.schetime.databinding.ActivitySignInBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignInActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivitySignInBinding

    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        binding.buttonSignIn.setOnClickListener {
            viewModel.signIn(
                email = binding.editTextEmail.text.toString().trim(),
                password = binding.editTextPassword.text.toString().trim()
            )
        }

        viewModel.isLoggedIn.observe(this, {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        })

        viewModel.signIn.observe(this, {
            when (it) {
                is Resource.Success -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }
        })
    }
}