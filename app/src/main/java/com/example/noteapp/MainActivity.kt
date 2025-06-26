package com.example.noteapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.db.Note
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.db.NoteRepository
import com.example.noteapp.ui.NoteViewAdapter
import com.example.noteapp.ui.NoteViewModel
import com.example.noteapp.ui.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NoteViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = NoteDatabase.getInstance(this).noteDao
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(modelClass = NoteViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun initRecyclerView() {
        binding.noteList.layoutManager = LinearLayoutManager(this)
        adapter = NoteViewAdapter { note: Note -> listItemClicked(note) }
        displayNoteList()
    }

    private fun displayNoteList() {
        viewModel.notes.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    fun listItemClicked(note: Note) {
        viewModel.initUpdateOrDelete(note)
    }
}