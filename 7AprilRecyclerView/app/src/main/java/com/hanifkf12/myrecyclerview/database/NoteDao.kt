package com.hanifkf12.myrecyclerview.database

import androidx.room.*
import com.hanifkf12.myrecyclerview.Note

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

    @Query("DELETE FROM note_table")
    suspend fun deleteAll() : Int
}