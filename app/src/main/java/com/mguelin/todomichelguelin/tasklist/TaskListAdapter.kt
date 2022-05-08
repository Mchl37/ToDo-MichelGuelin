package com.mguelin.todomichelguelin.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mguelin.todomichelguelin.R

object ItemsDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.equals(newItem)
    }
}

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(ItemsDiffCallback) {

    var onClickDelete: (Task) -> Unit = {}
    var onClickEdit: (Task) -> Unit = {}

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: Task) {

            val titleView = itemView.findViewById<TextView>(R.id.task_title)
            titleView.text = task.title
            val descView = itemView.findViewById<TextView>(R.id.task_description)
            descView.text = task.description

            val deleteButton = itemView.findViewById<Button>(R.id.delete)
            deleteButton.setOnClickListener { onClickDelete(task) }
            val editButton = itemView.findViewById<Button>(R.id.edit)
            editButton.setOnClickListener { onClickEdit(task) }
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
            return TaskViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            holder.bind(currentList[position])
        }
}