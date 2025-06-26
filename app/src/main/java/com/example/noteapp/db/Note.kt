package com.example.noteapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
// @Entity room keyword is used to declare the database and
@Entity(tableName = "note_data_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val id: Int,
    @ColumnInfo(name = "note_title")
    var title: String,
    @ColumnInfo(name = "note_text")
    var text: String
)
