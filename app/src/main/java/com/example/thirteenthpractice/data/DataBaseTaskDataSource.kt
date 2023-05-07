package com.example.thirteenthpractice.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseTaskDataSource(context: Context) : TaskDataSource,
    SQLiteOpenHelper(context, "tasks.db", null, 1) {

    companion object {
        private const val DATABASE_NAME = "tasks.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "tasks"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IS_DONE = "is_done"
    }

    override fun getTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_DESCRIPTION, COLUMN_IS_DONE),
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val isDone = getInt(getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1
                tasks.add(Task(description, isDone))
            }
        }
        db.close()
        return tasks
    }


    override fun addTask(task: Task) {
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_IS_DONE, if (task.isDone) 1 else 0)
        }
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    override fun deleteTask(task: Task) {
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_IS_DONE, if (task.isDone) 1 else 0)
        }
        db.delete(TABLE_NAME, "$COLUMN_DESCRIPTION = ?", arrayOf(task.description))
        db.close()
    }

    override fun updateTask(task: Task) {
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_IS_DONE, if (task.isDone) 1 else 0)
        }
        db.update(TABLE_NAME, value, "$COLUMN_DESCRIPTION = ?", arrayOf(task.description))
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // create database
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME ($COLUMN_DESCRIPTION TEXT PRIMARY KEY, $COLUMN_IS_DONE INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // upgrade database
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }
}