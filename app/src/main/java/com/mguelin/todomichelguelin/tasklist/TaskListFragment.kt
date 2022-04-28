package com.mguelin.todomichelguelin.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mguelin.todomichelguelin.R
import java.util.*

class TaskListFragment : Fragment() {

    private val adapter = TaskListAdapter()
    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        adapter.currentList = taskList
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_main)
        recyclerView.adapter = this.adapter
        val button = view.findViewById<ImageButton>(R.id.floatingActionButton)
            button.setOnClickListener {
                val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
                taskList = taskList + newTask
                
        }
    }
}