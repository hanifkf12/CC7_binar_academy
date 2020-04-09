package com.hanifkf12.myrecyclerview

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_note.view.*
import kotlinx.android.synthetic.main.dialog_edit_note.view.*
import java.text.FieldPosition
import kotlin.random.Random

class MainActivity : AppCompatActivity(){
    private lateinit var noteList : MutableList<Note>
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        noteList = mutableListOf()
        noteList.add(Note("Work From Home", "Finish my  task in the morning"))
        noteList.add(Note("Wake Up", "Bangun dari mimpi indah"))
        noteList.add(Note("Work From Home", "Finish my  task in the morning"))
        noteList.add(Note("Work From Home", "Finish my  task in the morning"))
        noteList.add(Note("Work From Home", "Finish my  task in the morning"))
        noteList.add(Note("Wake Up", "Bangun dari mimpi indah"))
        noteList.add(Note("Wake Up", "Bangun dari mimpi indah"))
        noteList.add(Note("Wake Up", "Bangun dari mimpi indah"))
        noteList.add(Note("Wake Up", "Bangun dari mimpi indah"))
        noteList.add(Note("Wake Up", "Bangun dari mimpi indah"))
        adapter = NoteAdapter(noteList)
//        val containerView = findViewById<>()
        adapter.setOnClickListener(object : NoteAdapter.OnClickListenerCallback{
            override fun onClickListener(note: Note, position: Int) {
                Toast.makeText(this@MainActivity, note.title,Toast.LENGTH_SHORT).show()
                showEditDialog(note,position)
//                Snackbar.make(containerView,note.title, Snackbar.LENGTH_SHORT).show()
            }

        })
        fab_add_data.setOnClickListener {
            showAddDialog()
        }

        rv_note.layoutManager = GridLayoutManager(this,2)
        rv_note.setHasFixedSize(true)
        rv_note.adapter = adapter
    }

    private fun showAddDialog() {
        this.let {
            val builder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
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
                    noteList.add(Note(title,content))
                    adapter.notifyDataSetChanged()
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
                noteList[position] = Note(title,content)
                adapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("DELETE"){_,_->
                noteList.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            val dialog = builder.create()
            dialog.show()

//            myView.textView2.setOnClickListener {
//                dialog.dismiss()
//            }
        }
    }
}
