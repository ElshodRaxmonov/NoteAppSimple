package com.example.noteapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.db.Note
import com.example.noteapp.db.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(val repository: NoteRepository) : ViewModel() {
    val notes = repository.notes
    var inputNoteTitle = MutableLiveData<String>("")
    var inputNote = MutableLiveData<String>("")
    var saveOrUpdateBtn = MutableLiveData<String>("Save")
    var deleteOrDeleteAllBtn = MutableLiveData<String>("Delete All")
    private var isUpdateOrDelete = false
    lateinit var noteUpdateOrDelete: Note
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateBtn.value = "Save"
        deleteOrDeleteAllBtn.value = "Delete All"
    }


    fun saveOrUpdate() {
        if (isUpdateOrDelete) {

            noteUpdateOrDelete.title = inputNoteTitle.value!!
            noteUpdateOrDelete.text = inputNote.value!!
            updateNote(noteUpdateOrDelete)
        } else {
            val title = inputNoteTitle.value!!.toString()
            val note = inputNote.value!!.toString()
            insertNote(Note(0, title, note))
            makeEmpty()
        }
    }

    fun deleteOrDeleteAll() {
        if (isUpdateOrDelete) {
            deleteNote(noteUpdateOrDelete)

        } else {
            deleteAll()
        }
    }


    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
        withContext(Dispatchers.Main) {
            statusMessage.value = Event("Note inserted Successfully")
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        val rowUpdated = repository.updateNote(note)
        withContext(Dispatchers.Main) {
            makeEmpty()
            isUpdateOrDelete = false
            saveOrUpdateBtn.value = "Save"
            deleteOrDeleteAllBtn.value = "Delete All"
            withContext(Dispatchers.Main) {
                if (rowUpdated>0)
                statusMessage.value = Event("$rowUpdated-note updated Successfully")
                else
                statusMessage.value = Event("Error Occurred")

            }
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        val rowDeleted = repository.deleteNote(note)
        withContext(Dispatchers.Main) {
            makeEmpty()
            isUpdateOrDelete = false
            saveOrUpdateBtn.value = "Save"
            deleteOrDeleteAllBtn.value = "Delete All"
            withContext(Dispatchers.Main) {
                if (rowDeleted>0)
                    statusMessage.value = Event("$rowDeleted-note deleted Successfully")
                else
                    statusMessage.value = Event("Error Occurred")
            }
        }
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
        withContext(Dispatchers.Main) {
            statusMessage.value = Event("All note deleted Successfully")
        }
    }

    fun initUpdateOrDelete(note: Note) {
        inputNote.value = note.text
        inputNoteTitle.value = note.title
        isUpdateOrDelete = true
        noteUpdateOrDelete = note
        saveOrUpdateBtn.value = "Update"
        deleteOrDeleteAllBtn.value = "Delete"
    }
    fun makeEmpty(){
        inputNote.value = ""
        inputNoteTitle.value = ""
    }
}