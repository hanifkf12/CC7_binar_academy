package com.hanifkf12.mynoteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hanifkf12.mynoteapp.dao.NoteDao
import com.hanifkf12.mynoteapp.entity.Note

@Database(entities = [Note::class] , version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase(){

    abstract fun noteDao() : NoteDao

    companion object{
        @Volatile
        private var INSTANCE : MyDatabase? = null
        fun getDatabase(context: Context) : MyDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}