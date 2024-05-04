package com.sqllitedemo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DataBaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DataBaseConstants.DATABASE_NAME,
    null,
    DataBaseConstants.DATABASE_VERSION
) {


    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
                  CREATE TABLE ${DataBaseConstants.TABLE_NAME} (
                         ${DataBaseConstants.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                         ${DataBaseConstants.COLUMN_NAME} TEXT,
                           ${DataBaseConstants.COLUMN_AGE} INTEGER)
        """.trimMargin()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion<newVersion){
            if (db != null) {
                db.execSQL("ALTER TABLE ${DataBaseConstants.TABLE_NAME} ADD COLUMN ${DataBaseConstants.COLUMN_HOBBY} TEXT DEFAULT 'CODING'")
            }
        }
    }

    fun readData(): List<User> {
        val datalist = mutableListOf<User>()

        val curser: Cursor = readableDatabase.query(
            DataBaseConstants.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null,
        )

        with(curser) {
            while (moveToNext()) {
                var id = getLong(getColumnIndexOrThrow(DataBaseConstants.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(DataBaseConstants.COLUMN_NAME))
                val age = getInt(getColumnIndexOrThrow(DataBaseConstants.COLUMN_AGE))
                val hobby = getString(getColumnIndexOrThrow(DataBaseConstants.COLUMN_HOBBY))
                datalist.add(User(id, name, age,hobby))
            }

        }
        return datalist
    }


    fun insertData(name: String, age: Int): Long {

        val values = ContentValues().apply {
            put(DataBaseConstants.COLUMN_NAME, name)
            put(DataBaseConstants.COLUMN_AGE, age)

        }

        return writableDatabase.insert(DataBaseConstants.TABLE_NAME, null, values)
    }
    fun insertData(name: String, age: Int,hobby: String): Long {

        val values = ContentValues().apply {
            put(DataBaseConstants.COLUMN_NAME, name)
            put(DataBaseConstants.COLUMN_AGE, age)
            put(DataBaseConstants.COLUMN_HOBBY, hobby)

        }

        return writableDatabase.insert(DataBaseConstants.TABLE_NAME, null, values)
    }

    fun delete(id: Long) : Int{
        val selection = "${DataBaseConstants.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return writableDatabase.delete(DataBaseConstants.TABLE_NAME,selection,selectionArgs)
    }
    fun update(id: Long,name: String,age : Int) : Int{
        val values = ContentValues().apply {
            put(DataBaseConstants.COLUMN_NAME, name)
            put(DataBaseConstants.COLUMN_AGE, age)

        }
        val selection = "${DataBaseConstants.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return writableDatabase.update(DataBaseConstants.TABLE_NAME,values, selection, selectionArgs)
    }
}