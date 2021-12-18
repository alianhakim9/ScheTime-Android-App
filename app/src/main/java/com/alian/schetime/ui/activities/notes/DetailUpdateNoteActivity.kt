package com.alian.schetime.ui.activities.notes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.data.model.Note
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import com.alian.schetime.ui.base.viewmodels.factory.NoteViewModelFactory
import com.alian.schetime.utils.Resource
import com.alian.schetime.utils.snackBar
import com.example.schetime.R
import com.example.schetime.databinding.ActivityDetailNoteBinding
import com.google.android.material.datepicker.MaterialDatePicker
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class DetailUpdateNoteActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityDetailNoteBinding

    override val kodein by kodein()

    private val factory: NoteViewModelFactory by instance()
    lateinit var viewModel: NoteViewModel

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val note = intent.getSerializableExtra("extra_note") as Note
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        binding.editTextTitle.setText(note.title)
        binding.editText.setText(note.text)
        binding.textViewTime.text = note.time
        binding.textViewTime.visibility = View.VISIBLE

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.done -> {
                    // update note here
                    val updatedNote = Note(note.id,
                        binding.editTextTitle.text.toString().trim(),
                        binding.editText.text.toString().trim(),
                        binding.textViewTime.text.toString(), note.userId
                    )
                    viewModel.updateNote(updatedNote)
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
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


        viewModel.updateNote.observe(this, {
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