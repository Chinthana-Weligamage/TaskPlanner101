package lk.cw.taskplanner101

// TaskDatabaseHelper.kt

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "taskplanner101.db"
        const val TABLE_NAME = "tasks"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DEADLINE = "deadline"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_DEADLINE TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertTask(title: String, description: String, deadline: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_DEADLINE, deadline)
        }
        val id = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return id
    }

    fun deleteTask(id: Number): Int {
        val db = this.writableDatabase
        val selection = "${TaskDatabaseHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val deletedRows = db.delete(TaskDatabaseHelper.TABLE_NAME, selection, selectionArgs)
        db.close()
        return deletedRows
    }

}
