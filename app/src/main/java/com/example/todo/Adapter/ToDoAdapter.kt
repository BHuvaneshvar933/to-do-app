package com.example.todo.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.MainActivity
import com.example.todo.Model.ToDoModel
import com.example.todo.R
import android.os.Bundle
import android.widget.CheckBox
import com.example.todo.AddNewTask
import com.example.todo.Utils.DatabaseHandler


class ToDoAdapter(private val db: DatabaseHandler,private val activity: MainActivity) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>(){

    private var todoList: MutableList<ToDoModel> = mutableListOf()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    fun setTasks(todoList: List<ToDoModel>) {
        this.todoList = todoList.toMutableList()
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task: CheckBox = view.findViewById(R.id.todoCheckBox)
    }
    fun deleteItem(position : Int){
        val item : ToDoModel = todoList.get(position)
        db.deleteTasks(item.getId())
        todoList.removeAt(position)
        notifyItemRemoved(position)

    }

    fun editItem(position : Int){
        val item : ToDoModel = todoList.get(position)
        val bundle = Bundle()
        bundle.putInt("id",item.getId())
        bundle.putString("task",item.getTask())
        val fragment = AddNewTask()
        fragment.arguments = bundle
        fragment.show(activity.supportFragmentManager, AddNewTask.TAG)
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ViewHolder, position: Int) {
        db.openDatabase()
        val item = todoList[position]
        holder.task.text = item.getTask()
        holder.task.isChecked = toBoolean(item.getStatus())
        holder.task.setOnCheckedChangeListener {_, isChecked ->
            if(isChecked){
                db.updateStatus(item.getId(), 1)

            }
            else{
                db.updateStatus(item.getId(), 0)
            }

        }
    }

    private fun toBoolean(i: Int) : Boolean = i!=0

    fun getContext() : Context{
        return activity
    }

    override fun getItemCount(): Int {
        return todoList.size
    }



}