package com.alian.schetime.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.alian.schetime.ui.activities.HomeActivity
import com.alian.schetime.ui.activities.notes.DetailUpdateNoteActivity
import com.alian.schetime.ui.base.adapters.NoteAdapter
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import com.alian.schetime.utils.Resource
import com.alian.schetime.utils.snackBar
import com.example.schetime.R
import com.example.schetime.databinding.FragmentDashboardBinding
import java.io.Serializable

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var _adapter: NoteAdapter
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        setupRecyclerView()
        viewModel = (activity as HomeActivity).viewModel
        viewModel.loadNotes()
        viewModel.userProfile()
        viewModel.notes.observe(this, {
            when (it) {
                is Resource.Success -> {
                    _adapter.differ.submitList(it.data)
                    binding.progressCircular.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.root.snackBar(it.message!!)
                    binding.progressCircular.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }

                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                else -> {}
            }
        })
        viewModel.user.observe(this, {
            binding.textViewGreeting.text = it.name
        })

        binding.imageViewUser.load(R.drawable.ic_avatar_user)

        _adapter.setOnItemClickListener { note ->
            Intent(context, DetailUpdateNoteActivity::class.java).also {
                it.putExtra("extra_note", note as Serializable)
                startActivity(it)
            }
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        _adapter = NoteAdapter()
        binding.recyclerView.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadNotes()
    }
}