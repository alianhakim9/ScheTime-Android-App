package com.alian.schetime.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alian.schetime.ui.activities.AddNoteActivity
import com.alian.schetime.ui.activities.HomeActivity
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import com.alian.schetime.utils.Resource
import com.alian.schetime.utils.snackBar
import com.example.schetime.databinding.FragmentAddNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.buttonToAddNoteActivity.setOnClickListener {
            Intent(context, AddNoteActivity::class.java).also {
                startActivity(it)
                dismiss()
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}