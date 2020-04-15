package com.hanifkf12.myrecyclerview.helper

import android.database.Cursor
import com.hanifkf12.myrecyclerview.Note
import com.hanifkf12.myrecyclerview.database.DatabaseContract

object MappingHelper {
    fun mapCursorToList(noteCursor: Cursor?, onComplete : (List<Note>) -> Unit){
        val noteList = mutableListOf<Note>()

        noteCursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                val content = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.CONTENT))
                noteList.add(Note(id,title,content))
            }
        }

        onComplete(noteList)
    }

}