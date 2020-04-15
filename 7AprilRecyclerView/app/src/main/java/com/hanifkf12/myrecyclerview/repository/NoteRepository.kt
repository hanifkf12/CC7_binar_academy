package com.hanifkf12.myrecyclerview.repository


import com.hanifkf12.myrecyclerview.Note
import com.hanifkf12.myrecyclerview.database.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun getNotes() : List<Note>{
        return noteDao.getNotes()
    }

    suspend fun insertNote(note : Note){
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.update(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.delete(note)
    }

}

