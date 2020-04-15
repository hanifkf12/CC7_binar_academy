package com.hanifkf12.myrecyclerview

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int? = 0,

    @ColumnInfo(name = "title")
    var title : String,

    @ColumnInfo(name = "content")
    var content : String
)