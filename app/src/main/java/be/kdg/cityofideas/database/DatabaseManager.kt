package be.kdg.cityofideas.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log

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

    fun getDetails(
        table: String,
        projection: Array<String>? = null,
        selection: String,
        selectionArgs: Array<String>,
        group: String? = null,
        filter: String? = null,
        order: String? = null
    ): Cursor {
        return db.query(table, projection, selection, selectionArgs, group, filter, order)
    }

    fun insert(tblName: String, values: ContentValues): Boolean {
        var inserted: Long = -1
        try {
            inserted = db.insert(tblName, null, values)

            // if insert failed, try replace
            if (inserted.toInt() == -1) {
                inserted = db.replace(tblName, null, values)
                Log.d("replacing", "ja")
            }
        } catch (e: SQLiteException) {
//            e.printStackTrace()
        }
        return inserted.toInt() != -1
    }

    fun delete(tblName: String): Boolean {
        try {
            db.delete(tblName, null, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        return true
    }

    fun update(tblName: String, values: ContentValues, selection: String, selectionargs: Array<String>): Int {
        return db.update(tblName, values, selection, selectionargs)
    }

    fun querySearch(
        table: String,
        selection: String,
        selectionArgs: Array<String>
    ): Cursor {
        return db.query(table, null, selection, selectionArgs, null, null, null)
    }
}
