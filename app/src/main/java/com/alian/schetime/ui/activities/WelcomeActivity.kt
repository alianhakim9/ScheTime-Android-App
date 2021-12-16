package com.alian.schetime.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.ui.activities.auth.SignInActivity
import com.alian.schetime.ui.activities.auth.SignUpActivity
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import com.alian.schetime.ui.base.viewmodels.factory.AuthViewModelFactory
import com.example.schetime.databinding.ActivityWelcomeBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class WelcomeActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityWelcomeBinding
    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            Intent(this, SignInActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnRegister.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

        viewModel.isLoggedIn.observe(this, {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        })
    }
}