package com.hanifkf12.myrecyclerview.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class NoteColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "note_table"
            const val _ID = "_id"
            const val TITLE = "title"
            const val CONTENT = "content"
        }
    }
}