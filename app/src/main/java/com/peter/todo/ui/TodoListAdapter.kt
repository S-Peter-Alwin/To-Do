package com.peter.todo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peter.todo.databinding.ItemTodoBinding
import com.peter.todo.db.ToDoEntity

class TodoListAdapter(private val onItemClick: (ToDoEntity) -> Unit): RecyclerView.Adapter<TodoListAdapter.TodoHolder>() {

    private  var datalist:List<ToDoEntity> = ArrayList()


    inner class TodoHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo: ToDoEntity) {
            itemView.setOnClickListener { onItemClick(todo) }
            binding.taskName.text = todo.todo
            binding.isCompleted.isChecked = todo.completed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val binding = ItemTodoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoHolder(binding)
    }



    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.bind(datalist.get(position))

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun submitList(list: List<ToDoEntity>?) {
        datalist = list!!
        notifyDataSetChanged()
    }
}

