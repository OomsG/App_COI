package be.kdg.cityofideas.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.IllegalStateException

class DatabaseManager(context: Context) {
    private lateinit var db: SQLiteDatabase
    var dbHelper = DatabaseHelper(context)

    fun closeDatabase() {
        db.close()
    }

    fun openDatabase() {
        db = dbHelper.writableDatabase
    }

    fun getDetails(query: String): Cursor? {
        return db.rawQuery(query, null)
    }

    fun insert(tblName: String, values: ContentValues): Boolean {
        var inserted: Long = -1;
        try {
            inserted = db.insert(tblName, null, values)
            closeDatabase()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        return inserted.toInt() != -1;
    }

    fun delete(tblName: String): Boolean {
        try {
            db.delete(tblName, null, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        return true
    }

//    fun update(tblName: String, values: ContentValues, selection: String, selectionargs: Array<String>): Int {
//
//        val count = db!!.update(tblName, values, selection, selectionargs)
//        return count
//    }
}
