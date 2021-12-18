package com.alian.schetime.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.alian.schetime.ui.activities.HomeActivity
import com.alian.schetime.ui.activities.compose.WelcomeActivityCompose
import com.alian.schetime.ui.activities.user.EditUserActivity
import com.alian.schetime.ui.activities.notes.DetailUpdateNoteActivity
import com.alian.schetime.ui.base.adapters.MyNoteAdapter
import com.alian.schetime.ui.base.adapters.NoteAdapter
import com.alian.schetime.ui.base.viewmodels.NoteViewModel
import com.alian.schetime.utils.Resource
import com.alian.schetime.utils.snackBar
import com.example.schetime.R
import com.example.schetime.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var _adapter: MyNoteAdapter
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = (activity as HomeActivity).viewModel

        viewModel.userProfile()
        setupRecyclerView()
        viewModel.user.observe(this, {
            binding.textViewUserName.text = it.name
            binding.textViewJobTitle.text = it.jobTitle
        })

        binding.imageViewUser.load(R.drawable.ic_avatar_user)
        viewModel.notes.observe(this, {
            when (it) {
                is Resource.Success -> {
                    _adapter.differ.submitList(it.data)
                    binding.recyclerView.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.root.snackBar(it.message!!)
                    binding.recyclerView.visibility = View.VISIBLE
                }

                is Resource.Loading -> {
                    binding.recyclerView.visibility = View.GONE
                }
                else -> {}
            }
        })

        binding.buttonEditProfile.setOnClickListener {
            Intent(context, EditUserActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.imageViewMenu.setOnClickListener {
            showMenu(binding.imageViewMenu, R.menu.popup_menu)
        }

        _adapter.setOnItemClickListener { note ->
            Intent(context, DetailUpdateNoteActivity::class.java).also {
                it.putExtra("extra_note", note as Serializable)
                startActivity(it)
            }
        }

        _adapter.setOnLongItemClickListener { note ->
            val callback = object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.note_action_menu, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    return when (item?.itemId) {
                        R.id.delete -> {
                            mode?.finish()
                            MaterialAlertDialogBuilder(context!!)
                                .setTitle(note.title)
                                .setMessage("Delete ${note.title}")
                                .setNegativeButton("cancel") { dialog, _ -> dialog.cancel() }
                                .setPositiveButton("yes") { dialog, _ ->
                                    // do delete note here
                                    viewModel.deleteNote(note)
                                    viewModel.loadNotes()
                                    dialog.dismiss()
                                }.show()
                            true
                        }
                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    mode?.finish()
                }
            }
            val actionMode = (activity as HomeActivity).startActionMode(callback)
            actionMode?.title = note.title
        }

        _adapter.setOnCheckItemListener { note ->
            Snackbar.make(binding.root,
                "${note.title} mark as done",
                Snackbar.LENGTH_LONG)
                .setAction("undo") {
                    // Responds to click on the action
                    viewModel.addNote(note.title, note.text)
//                    viewModel.loadNotes()
                }
                .show()
            viewModel.deleteNote(note)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        _adapter = MyNoteAdapter()
        binding.recyclerView.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadNotes()
        viewModel.userProfile()
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(context!!, v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            when (menuItem.itemId) {
                R.id.logout -> {
                    MaterialAlertDialogBuilder(context!!)
                        .setTitle("Log out ?")
                        .setMessage("are you sure want to logout ?")
                        .setNegativeButton("cancel") { dialog, _ ->
                            // Respond to negative button press
                            dialog.dismiss()
                        }
                        .setPositiveButton("log out") { dialog, _ ->
                            // Respond to positive button press
                            viewModel.logout()
                            Intent(context, WelcomeActivityCompose::class.java).also { intent ->
                                startActivity(intent)
                                (activity as HomeActivity).finish()
                            }
                            dialog.dismiss()
                        }
                        .show()
                    true
                }
                else -> false
            }
        }
        // Show the popup menu.
        popup.gravity = Gravity.END
        popup.show()
    }
}