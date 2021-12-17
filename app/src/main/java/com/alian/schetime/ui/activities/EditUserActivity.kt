package com.alian.schetime.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.alian.schetime.data.model.User
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import com.alian.schetime.ui.base.viewmodels.factory.NoteViewModelFactory
import com.alian.schetime.utils.Resource
import com.alian.schetime.utils.snackBar
import com.example.schetime.R
import com.example.schetime.databinding.ActivityEditUserBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EditUserActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityEditUserBinding

    override val kodein by kodein()

    private val factory: NoteViewModelFactory by instance()
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        binding.imageViewUser.load(R.drawable.ic_avatar_user)

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        viewModel.userProfile()
        viewModel.user.observe(this, {
            binding.editTextFullName.editText?.setText(it.name)
            binding.editTextEmail.editText?.setText(it.email)
            binding.editTextPassword.editText?.setText(it.password)
        })

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.done -> {
                    val updatedUser =
                        User(0,
                            name = binding.editTextFullName.editText?.text.toString().trim(),
                            email = binding.editTextEmail.editText?.text.toString().trim(),
                            password = binding.editTextPassword.editText?.text.toString().trim()
                        )
                    viewModel.updateProfile(updatedUser)
                    true
                }
                else -> {
                    false
                }
            }
        }

        viewModel.updateProfile.observe(this, {
            when (it) {
                is Resource.SuccessWithoutData -> {
                    finish()
                }

                is Resource.Error -> {
                    binding.root.snackBar(it.message!!)
                }
                else -> {}
            }
        })
    }
}