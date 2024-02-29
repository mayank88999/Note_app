package com.example.note_app.models

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.edit
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app.R
import com.example.note_app.R.layout.note
import com.example.note_app.data.Note


class NoteAdapter(private val noteDao: NoteDao, private val context: Context): ListAdapter<Note, NoteAdapter.NoteViewHolder>(
    DiffCallback()
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("NoteData", Context.MODE_PRIVATE)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        fun bind(note: Note) {

            titleTextView.text = note.title
            contentTextView.text = note.content

         editButton.setOnClickListener {
             sharedPreferences.edit {
                 putLong("noteId", note.id)
                 putString("noteTitle", note.title)
                 putString("noteContent", note.content)
                 apply()
             }
             itemView.findNavController().navigate(R.id.action_notes_fragment_to_edit_fragment)
            }

           deleteButton.setOnClickListener {
               val context = itemView.context

              noteDao.deleteNoteById(note.id)

               notifyItemRemoved(adapterPosition)
            }
        }
    }
    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}