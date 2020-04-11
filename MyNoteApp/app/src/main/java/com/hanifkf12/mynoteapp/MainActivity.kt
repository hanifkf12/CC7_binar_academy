package com.hanifkf12.mynoteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanifkf12.mynoteapp.database.MyDatabase
import com.hanifkf12.mynoteapp.entity.Note
import com.hanifkf12.mynoteapp.repository.NoteRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var myDatabase: MyDatabase
    private lateinit var noteRepository: NoteRepository
    private lateinit var noteList : MutableList<Note>
    private lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDatabase = MyDatabase.getDatabase(this)
        noteRepository = NoteRepository(myDatabase.noteDao())
        noteList = mutableListOf()
        adapter = MainAdapter(noteList)
        rv_notes.layoutManager = GridLayoutManager(this,2)
        rv_notes.setHasFixedSize(true)
        rv_notes.adapter = adapter
        loadNotes()
        fab_add_note.setOnClickListener {
            GlobalScope.launch {
                noteRepository.insertNote(Note(null,"Makan Harimau ${Random().nextInt(1000)}",
                    "Makan Ikan dan sambal ${Random().nextInt(1000)}"))
            }
            loadNotes()
        }


    }
    private fun loadNotes(){
        GlobalScope.launch(Dispatchers.Main) {
            val notes = noteRepository.getNotes()
            noteList.clear()
            noteList.addAll(notes)
            adapter.notifyDataSetChanged()
        }
    }
}
