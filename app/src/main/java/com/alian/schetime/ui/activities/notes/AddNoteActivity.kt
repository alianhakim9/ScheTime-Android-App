package com.alian.schetime.ui.activities.notes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import com.alian.schetime.ui.base.viewmodels.factory.NoteViewModelFactory
import com.alian.schetime.utils.Resource
import com.alian.schetime.utils.snackBar
import com.example.schetime.R
import com.example.schetime.databinding.ActivityAddNoteBinding
import com.google.android.material.datepicker.MaterialDatePicker
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityAddNoteBinding

    override val kodein by kodein()

    private val factory: NoteViewModelFactory by instance()
    lateinit var viewModel: NoteViewModel

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.done -> {
                    viewModel.addNote(
                        title = binding.editTextTitle.text.toString().trim(),
                        description = binding.editText.text.toString().trim(),
                    )
                    true
                }
                else -> false
            }
        }

        binding.textViewTimePicker.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select deadline")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                calendar.timeInMillis = datePicker.selection!!
                val deadline = formatter.format(calendar.time)
                binding.textViewTime.visibility = View.VISIBLE
                binding.textViewTime.text = deadline
                viewModel.noteTime.postValue(deadline)
            }

            datePicker.show(supportFragmentManager, "tag")
        }
        viewModel.addNote.observe(this, {
            when (it) {
                is Resource.SuccessWithoutData -> {
                    finish()
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    binding.root.snackBar(it.message!!)
                }
                else -> {}
            }
        })
    }
}