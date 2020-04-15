package com.hanifkf12.myrecyclerview

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
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
import com.hanifkf12.myrecyclerview.database.DatabaseContract
import com.hanifkf12.myrecyclerview.database.NoteHelper
import com.hanifkf12.myrecyclerview.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_note.view.*
import kotlinx.android.synthetic.main.dialog_edit_note.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.FieldPosition
import kotlin.random.Random

class MainActivity : AppCompatActivity(){
    private lateinit var noteList : MutableList<Note>
    private lateinit var adapter: NoteAdapter
    private lateinit var noteHelper: NoteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        noteHelper = NoteHelper.getInstance(this)
        noteHelper.open()
        noteList = mutableListOf()
        adapter = NoteAdapter(noteList)
//        val containerView = findViewById<>()
        adapter.setOnClickListener(object : NoteAdapter.OnClickListenerCallback<Note>{
            override fun onClickListener(data: Note, position: Int) {
                Toast.makeText(this@MainActivity, data.title,Toast.LENGTH_SHORT).show()
                showEditDialog(data,position)
//                Snackbar.make(containerView,note.title, Snackbar.LENGTH_SHORT).show()
            }

        })
        fab_add_data.setOnClickListener {
            showAddDialog()
        }

        rv_note.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        rv_note.setHasFixedSize(true)
        rv_note.adapter = adapter
        loadNotesAsync()
    }

    private fun loadNotesAsync(){
        lifecycleScope.launch(Dispatchers.Main) {
            MappingHelper.mapCursorToList(noteHelper.queryAll()){
                noteList.clear()
                noteList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
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
                    val values = ContentValues()
                    values.put(DatabaseContract.NoteColumns.TITLE, title)
                    values.put(DatabaseContract.NoteColumns.CONTENT, content)
                    lifecycleScope.launch {
                        val result = noteHelper.insert(values)
                        if (result>0){
                            Toast.makeText(this@MainActivity,"BERHASIL DISIMPAN",Toast.LENGTH_SHORT).show()
                            loadNotesAsync()
                        }else{
                            Toast.makeText(this@MainActivity,"GAGAL DISIMPAN",Toast.LENGTH_SHORT).show()
                        }
                    }
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
                val values = ContentValues()
                values.put(DatabaseContract.NoteColumns.TITLE, title)
                values.put(DatabaseContract.NoteColumns.CONTENT, content)
                lifecycleScope.launch {
                    val result = noteHelper.update(note.id.toString(), values)
                    if(result>0){
                        Toast.makeText(this@MainActivity,"BERHASIL DIUPDATE",Toast.LENGTH_SHORT).show()
                        loadNotesAsync()
                    }else{
                        Toast.makeText(this@MainActivity,"GAGAL DIUPDATE",Toast.LENGTH_SHORT).show()

                    }
                }
            }
            builder.setNegativeButton("DELETE"){_,_->
                lifecycleScope.launch {
                    val result = noteHelper.deleteById(note.id.toString())
                    if(result>0){
                        Toast.makeText(this@MainActivity,"BERHASIL DIHAPUS",Toast.LENGTH_SHORT).show()
                        loadNotesAsync()
                    }else{
                        Toast.makeText(this@MainActivity,"GAGAL DIHAPUS",Toast.LENGTH_SHORT).show()

                    }
                }
            }
            val dialog = builder.create()
            dialog.show()

//            myView.textView2.setOnClickListener {
//                dialog.dismiss()
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        noteHelper.close()
    }
}
