package com.example.todo.Utils

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.fragment.app.FragmentActivity
import com.example.todo.Model.ToDoModel

class DatabaseHandler(context: FragmentActivity?) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val VERSION: Int = 1
        private const val NAME: String = "toDoListDatabase"
        private const val TODO_TABLE: String = "todo"
        private const val ID: String = "id"
        private const val TASK: String = "task"
        private const val STATUS: String = "status"
        private const val CREATE_TODO_TABLE: String =
            "CREATE TABLE $TODO_TABLE ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TASK TEXT, $STATUS INTEGER)"

    }

    private lateinit var db: SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TODO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //drop the older tables
        db?.execSQL("DROP TABLE IF EXISTS "+TODO_TABLE)
        //create tables again
        onCreate(db)
    }

    fun openDatabase(){
        db = this.writableDatabase

    }

    fun insertTask(task : ToDoModel){
        val cv : ContentValues = ContentValues()
        cv.put(TASK, task.getTask())
        cv.put(STATUS, 0)
        db.insert(TODO_TABLE, null, cv)
    }

    fun getAllTasks(): List<ToDoModel> {
        val taskList = mutableListOf<ToDoModel>()
        var cursor: Cursor? = null
        db.beginTransaction()
        try {
            cursor = db.query(
                TODO_TABLE,
                null, null, null,
                null, null, null, null
            )
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        val task = ToDoModel()
                        task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)))
                        task.setTask(cursor.getString(cursor.getColumnIndexOrThrow(TASK)))
                        task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(STATUS)))
                        taskList.add(task)
                    } while (it.moveToNext())
                }
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            cursor?.close()
        }
        return taskList
    }


    fun updateStatus(id : Int,status : Int){
        val cv : ContentValues = ContentValues()
        cv.put(STATUS, status)
        db.update(TODO_TABLE, cv, "$ID =?",arrayOf(id.toString()) )
    }

    fun updateTasks(id : Int, task : String){
        val cv : ContentValues = ContentValues()
        cv.put(TASK, task)
        db.update(TODO_TABLE, cv, "ID = ?", arrayOf(id.toString()))
    }

    fun deleteTasks(id : Int){
        db.delete(TODO_TABLE, "ID = ?", arrayOf(id.toString()))
    }
}