package com.example.note_app.models

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.note_app.data.Note

class NoteDao(context: Context) {
    private val db: SQLiteDatabase = DatabaseHelper(context).writableDatabase

    fun addNote(note: Note): Long {
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.KEY_TITLE, note.title)
            put(DatabaseHelper.KEY_CONTENT, note.content)
        }
        return db.insert(DatabaseHelper.TABLE_NOTES, null, contentValues)
    }

    @SuppressLint("Range")
    fun getAllNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NOTES}", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID))
                val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE))
                val content = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CONTENT))
                val note = Note(id, title, content)
                notes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return notes
    }

    fun updateNote(note: Note): Int {
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.KEY_TITLE, note.title)
            put(DatabaseHelper.KEY_CONTENT, note.content)
        }
        return db.update(DatabaseHelper.TABLE_NOTES, contentValues, "${DatabaseHelper.KEY_ID}=?", arrayOf(note.id.toString()))
    }

    fun deleteNoteById(id: Long): Int {
        return db.delete(DatabaseHelper.TABLE_NOTES, "${DatabaseHelper.KEY_ID}=?", arrayOf(id.toString()))
    }
}