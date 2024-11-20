package com.monk.bmi.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "bmiapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "historyBMI"
        private const val COLUMN_ID = "id"
        private const val COLUMN_HEIGHT = "height"
        private const val COLUMN_WEIGHT = "weight"
        private const val COLUMN_BMI = "bmi"
        private const val COLUMN_NOTE = "note"
        private const val COLUMN_CREATED_AT = "createdAt"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_HEIGHT REAL, $COLUMN_WEIGHT REAL, $COLUMN_BMI REAL, $COLUMN_NOTE TEXT, $COLUMN_CREATED_AT INTEGER)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertHistory(history: History) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_HEIGHT, history.height)
            put(COLUMN_WEIGHT, history.weight)
            put(COLUMN_BMI, history.bmi)
            put(COLUMN_NOTE, history.note)
            put(COLUMN_CREATED_AT, history.createdAt)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllHistory(): List<History> {
        val historyList = mutableListOf<History>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val bmi = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BMI))
            val createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT))
            val note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE))

            val history = History(id = id, bmi = bmi, createdAt = createdAt, note =  note)
            historyList.add(history)
        }
        cursor.close()
        db.close()
        return historyList
    }

    fun deleteHistory(id: Int) {
        val db = writableDatabase
        val where = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id.toString())
        db.delete(TABLE_NAME, where, whereArgs)
        db.close()
    }
}