package com.example.ss3_app_new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ss3_app_new.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(private var tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.taskTitle.text = task.title
        holder.binding.taskDescription.text = task.description

        // dd/MM/yyyy - date formatting
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.binding.taskDate.text = formatter.format(Date(task.date))
    }

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
