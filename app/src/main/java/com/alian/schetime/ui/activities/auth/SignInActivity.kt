package com.alian.schetime.ui.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.ui.activities.HomeActivity
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import com.alian.schetime.ui.base.viewmodels.factory.AuthViewModelFactory
import com.alian.schetime.utils.Resource
import com.alian.schetime.utils.snackBar
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

        viewModel.isLoggedIn.observe(this, {
            Intent(this, HomeActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                it.putExtra("EXIT", true)
                startActivity(it)
                finish()
            }
        })

        binding.buttonSignIn.setOnClickListener {
            viewModel.signIn(
                email = binding.editTextEmail.editText?.text.toString().trim(),
                password = binding.editTextPassword.editText?.text.toString().trim()
            )
        }

        viewModel.signIn.observe(this, { res ->
            when (res) {
                is Resource.SuccessWithoutData -> {
                    Intent(this, HomeActivity::class.java).also {
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        it.putExtra("EXIT", true)
                        startActivity(it)
                        finish()
                    }
                }

                is Resource.Error -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.buttonSignIn.visibility = View.VISIBLE
                    binding.root.snackBar(res.message!!)
                }

                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.buttonSignIn.visibility = View.INVISIBLE
                }
                else -> {}
            }
        })
    }
}