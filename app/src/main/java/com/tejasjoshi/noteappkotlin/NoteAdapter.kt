package com.tejasjoshi.noteappkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.list_layout.view.*

/**
 * Created by Tejas.Joshi on 6/5/2018.
 */
class NoteAdapter(val notes: ArrayList<NoteModel>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder?, position: Int) {

        if (holder != null) {
            holder.bindItems(notes[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteAdapter.ViewHolder {

        val v = LayoutInflater.from(parent!!.context).inflate(R.layout.list_layout, parent, false)
        return NoteAdapter.ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(noteModel: NoteModel) {
            itemView.textNoteTitle.text = noteModel.title;
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }
}