package com.example.noteapp.db

class NoteRepository(private val noteDAO: NoteDAO) {
    val notes = noteDAO.getAllNotes()

    suspend fun updateNote(note: Note):Int{
        return noteDAO.updateNote(note)
    }

    suspend fun insertNote(note: Note):Long {
       return  noteDAO.insertNote(note)
    }

    suspend fun deleteNote(note: Note):Int{
      return noteDAO.deleteNote(note)
    }

    fun deleteAll() :Int{
        return noteDAO.deleteAll()
    }
}