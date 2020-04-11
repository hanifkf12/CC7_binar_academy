package com.hanifkf12.mynoteapp.repository

import com.hanifkf12.mynoteapp.dao.NoteDao
import com.hanifkf12.mynoteapp.entity.Note

class NoteRepository (private val noteDao: NoteDao){
    suspend fun getNotes() : List<Note>{
        val notes = noteDao.getNotes()
        return notes
    }

    suspend fun insertNote(note: Note){
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.update(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.delete(note)
    }
}