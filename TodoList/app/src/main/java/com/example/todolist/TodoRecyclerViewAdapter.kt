package com.example.todolist

import android.graphics.Color.green
import android.graphics.Color.red
import android.icu.text.MessageFormat.format
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTodoBinding
import com.example.todolist.db.ToDoEntity
import java.lang.String.format
import android.text.format.DateFormat
import java.text.MessageFormat.format

class TodoRecyclerViewAdapter(private val todoList: ArrayList<ToDoEntity>,
private val listener :OnItemLongClickListener)
    : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>(){

        inner class MyViewHolder(binding: ItemTodoBinding):
        RecyclerView.ViewHolder(binding.root){
            val tv_importance = binding.tvImportance
            val tv_title = binding.tvTitle
            val tv_date = binding.tvDate

            val root = binding.root
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding:ItemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]

        when(todoData.importance){
            1 ->{
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2 ->{
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3 ->{
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }
        holder.tv_importance.text = todoData.importance.toString()
        holder.tv_date.text = DateFormat.format("yyyy/MM/dd",todoData.date)
        holder.tv_title.text = todoData.title
        holder.root.setOnClickListener {
            listener.onLongClick(position)
            false
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

}