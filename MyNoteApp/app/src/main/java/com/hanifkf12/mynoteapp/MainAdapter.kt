package com.hanifkf12.mynoteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hanifkf12.mynoteapp.entity.Note
import kotlinx.android.synthetic.main.item_note.view.*

class MainAdapter(private val notes : List<Note>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note){
            itemView.item_title.text = note.title
            itemView.item_content.text = note.content

        }
    }



}