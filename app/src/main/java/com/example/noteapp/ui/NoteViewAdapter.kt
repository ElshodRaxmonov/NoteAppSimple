package com.example.noteapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.NoteItemBinding
import com.example.noteapp.db.Note

class NoteViewAdapter(private val clickListener: (Note) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private var notes = ArrayList<Note>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: NoteItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.note_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.bind(notes.get(index = position), clickListener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setList(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
    }
}

class MyViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note, clickListener: (Note) -> Unit) {
        binding.noteTitle.text = note.title
        binding.note.text = note.text
        binding.noteCard.setOnClickListener {
            clickListener(note)
        }
    }
}