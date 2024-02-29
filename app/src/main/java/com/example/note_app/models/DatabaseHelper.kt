package com.example.note_app.models
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NotesDB"
        internal const val TABLE_NOTES = "notes"
        internal const val KEY_ID = "id"
        internal const val KEY_TITLE = "title"
        internal const val KEY_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NOTES ($KEY_ID INTEGER PRIMARY KEY, $KEY_TITLE TEXT, $KEY_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }
}