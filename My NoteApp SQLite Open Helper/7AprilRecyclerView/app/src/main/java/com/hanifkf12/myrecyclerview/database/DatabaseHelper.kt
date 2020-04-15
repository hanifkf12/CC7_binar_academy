package com.hanifkf12.myrecyclerview.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hanifkf12.myrecyclerview.database.DatabaseContract.NoteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "note_database"
        private const val DATABASE_VERSION = 1
        private var SQL_CREATE_NOTE_TABLE = "CREATE TABLE $TABLE_NAME" +
                "(${DatabaseContract.NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "${DatabaseContract.NoteColumns.TITLE} TEXT NOT NULL,"+
                "${DatabaseContract.NoteColumns.CONTENT} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_NOTE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}