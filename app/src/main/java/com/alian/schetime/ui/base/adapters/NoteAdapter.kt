package com.alian.schetime.ui.base.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alian.schetime.data.model.Note
import com.example.schetime.R
import com.example.schetime.databinding.ItemNoteBinding

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.DataViewHolder>() {

    private val limit = 3

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class DataViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.textViewTargetTime.text = note.title
            binding.textViewTitle.text = note.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val note = differ.currentList[position]
        holder.bind(differ.currentList[position])
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let {
                    it(note)
                }
            }

            setOnLongClickListener {
                onLongItemClickListener?.let {
                    it(note)
                }
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return if (differ.currentList.size < limit) {
            differ.currentList.size
        } else {
            limit
        }
    }

    private var onItemClickListener: ((Note) -> Unit)? = null

    private var onLongItemClickListener: ((Note) -> Unit)? = null

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnLongItemClickListener(listener: (Note) -> Unit) {
        onLongItemClickListener = listener
    }
}