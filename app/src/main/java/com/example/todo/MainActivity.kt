package com.example.todo

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.Adapter.ToDoAdapter
import com.example.todo.Model.ToDoModel
import com.example.todo.Utils.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections

class MainActivity : AppCompatActivity(), DialogCloseListener {
    private lateinit var tasksRecyclerView : RecyclerView
    private lateinit var taskAdapter : ToDoAdapter
    private var taskList: MutableList<ToDoModel> = mutableListOf()
    private lateinit var db : DatabaseHandler
    private lateinit var fab : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DatabaseHandler(this)
        db.openDatabase()
        supportActionBar?.hide()
        tasksRecyclerView = findViewById<RecyclerView>(R.id.tasksRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = ToDoAdapter(db,this)
        tasksRecyclerView.adapter = taskAdapter


        fab = findViewById<FloatingActionButton>(R.id.fab)

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(taskAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

       taskList = db.getAllTasks() as MutableList<ToDoModel>
        Collections.reverse(taskList)
        Log.d("MainActivity", "Tasks loaded: ${taskList.size}")

        taskAdapter.setTasks(taskList)
        fab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }
    }

    override fun handleDialogClose(dialog: DialogInterface) {
        taskList = db.getAllTasks() as MutableList<ToDoModel>
        Collections.reverse(taskList)
        taskAdapter.setTasks(taskList)
        taskAdapter.notifyDataSetChanged()
    }
}