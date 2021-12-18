package com.alian.schetime.ui.base.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alian.schetime.data.model.Note
import com.example.schetime.R
import com.example.schetime.databinding.ItemNoteBinding
import com.example.schetime.databinding.MyItemNoteBinding

class MyNoteAdapter : RecyclerView.Adapter<MyNoteAdapter.DataViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class DataViewHolder(private val binding: MyItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.textViewTargetTime.text = note.title
            binding.textViewTitle.text = note.text
        }

        val cb = binding.checkBoxDone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            MyItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        holder.cb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onCheckItemListener?.let {
                    it(note)
                    holder.cb.isChecked = false
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Note) -> Unit)? = null

    private var onLongItemClickListener: ((Note) -> Unit)? = null

    private var onCheckItemListener: ((Note) -> Unit)? = null

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnLongItemClickListener(listener: (Note) -> Unit) {
        onLongItemClickListener = listener
    }

    fun setOnCheckItemListener(listener: (Note) -> Unit) {
        onCheckItemListener = listener
    }
}