package com.example.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDAO {
    @Insert
    suspend fun insertNote(note: Note):Long

    @Delete
    suspend fun deleteNote(note: Note):Int

    @Update
    suspend fun updateNote(note: Note):Int

    @Query("DELETE FROM note_data_table")
    fun deleteAll():Int

    @Query("SELECT*FROM note_data_table")
    fun getAllNotes(): LiveData<List<Note>>
}