package com.alian.schetime.ui.activities.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.data.model.User
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import com.alian.schetime.ui.base.viewmodels.factory.AuthViewModelFactory
import com.alian.schetime.utils.Resource
import com.example.schetime.databinding.ActivitySignUpBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivitySignUpBinding

    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]


        binding.buttonSignUp.setOnClickListener {
            val newUser = User(
                id = 0,
                name = binding.editTextFullName.text.toString().trim(),
                email = binding.editTextEmail.text.toString().trim(),
                password = binding.editTextPassword.text.toString().trim()
            )
            viewModel.signUp(newUser)
        }

        viewModel.signUp.observe(this, {
            when (it) {
                is Resource.SuccessWithoutData -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.buttonSignUp.visibility = View.VISIBLE
                    Toast.makeText(this, "register success", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.buttonSignUp.visibility = View.GONE
                }

                is Resource.Error -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.buttonSignUp.visibility = View.VISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })
    }
}