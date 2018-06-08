package com.tejasjoshi.noteappkotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by Tejas.Joshi on 6/6/2018.
 */
class NoteDatabseHelper(context : Context) : SQLiteOpenHelper(context, "example.db", null, 4) {


    // Database Version
    private val DATABASE_VERSION = 1

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("NoteApp","OnCreate is getting called")
        //To change body of created functions use File | Settings | File Templates.
        if (db != null) {
            db.execSQL("create table Notes (id integer primary key autoincrement, title text, publisher text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertIntoData(title: String, publisher: String): Long {
        var db: SQLiteDatabase = this.writableDatabase

        var values: ContentValues = ContentValues();

        values.put("title", title);
        values.put("publisher", publisher);

        val result: Long = db.insert("Notes", null, values);

        db.close();

        return result;
    }

    fun getAllData(): ArrayList<NoteModel> {

        var Notelist: ArrayList<NoteModel> = ArrayList<NoteModel>()

        var db: SQLiteDatabase = this.readableDatabase

        var curser: Cursor = db.rawQuery("Select * from Notes", null);

        if (curser.moveToFirst()) {
            do {

                var noteModel: NoteModel = NoteModel(curser.getInt(0), curser.getString(curser.getColumnIndex("title")), curser.getString(curser.getColumnIndex("publisher")))

                Notelist.add(noteModel)

            } while (curser.moveToNext())
        }

        return Notelist;

    }

}