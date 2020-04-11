package com.hanifkf12.mynoteapp.dao

import androidx.room.*
import com.hanifkf12.mynoteapp.entity.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    suspend fun getNotes() : List<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

}