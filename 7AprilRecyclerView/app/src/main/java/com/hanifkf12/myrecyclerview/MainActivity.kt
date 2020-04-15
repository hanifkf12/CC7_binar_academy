package com.hanifkf12.myrecyclerview

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hanifkf12.myrecyclerview.database.MyDatabase
import com.hanifkf12.myrecyclerview.repository.NoteRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_note.view.*
import kotlinx.android.synthetic.main.dialog_edit_note.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.text.FieldPosition
import kotlin.random.Random

class MainActivity : AppCompatActivity(){
    private lateinit var noteList : MutableList<Note>
    private lateinit var adapter: NoteAdapter
    private lateinit var database: MyDatabase
    private lateinit var repository: NoteRepository
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = MyDatabase.getDatabase(this)
        repository = NoteRepository(database.noteDao())
        noteList = mutableListOf()
        adapter = NoteAdapter(noteList)
        adapter.setOnClickListener(object : NoteAdapter.OnClickListenerCallback<Note>{
            override fun onClickListener(data: Note, position: Int) {
                Toast.makeText(this@MainActivity, data.title,Toast.LENGTH_SHORT).show()
                showEditDialog(data,position)
            }
        })
        fab_add_data.setOnClickListener {
            showAddDialog()
        }
        rv_note.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        rv_note.setHasFixedSize(true)
        rv_note.adapter = adapter
        loadNotes()

    }

    private fun loadNotes(){
        lifecycleScope.launch(Dispatchers.Main) {
            val notes = repository.getNotes()
            noteList.clear()
            noteList.addAll(notes)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showAddDialog() {
        this.let {
            val builder = AlertDialog.Builder(it)
            val inflater = it.layoutInflater
            val myView = inflater.inflate(R.layout.dialog_add_note,null)
            builder.setView(myView)
            val dialog = builder.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            myView.btn_simpan.setOnClickListener {
                val title = myView.edit_title.text.toString()
                val content = myView.edit_content.text.toString()
                if (title.isEmpty() || content.isEmpty()){
                    Toast.makeText(this, "ISI dulu DATANYA", Toast.LENGTH_SHORT).show()
                }else{
                    //Menyimpan Data
                    val note = Note(null, title, content)
                    lifecycleScope.launch {
                        repository.insertNote(note)
                    }
                    loadNotes()
                    dialog.dismiss()
                }

            }
            myView.btn_cancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }


    fun showEditDialog(note: Note, position: Int){
        this.let {
            val builder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val myView = inflater.inflate(R.layout.dialog_edit_note,null)
            myView.edit_title_ku.setText(note.title)
            myView.edit_content_ku.setText(note.content)
            builder.setView(myView)
            builder.setNeutralButton("BATAL"){dialog,_->
                dialog.cancel()
            }
            builder.setPositiveButton("UPDATE"){_,_->
                val title = myView.edit_title_ku.text.toString()
                val content = myView.edit_content_ku.text.toString()
                note.title = title
                note.content = content
                lifecycleScope.launch {
                    repository.updateNote(note)
                }
                loadNotes()
            }
            builder.setNegativeButton("DELETE"){_,_->
                lifecycleScope.launch {
                    repository.deleteNote(note)
                }
                loadNotes()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}
