package com.mguelin.todomichelguelin.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mguelin.todomichelguelin.R
import com.mguelin.todomichelguelin.network.Api
import com.mguelin.todomichelguelin.form.FormActivity
import com.mguelin.todomichelguelin.user.UserInfoActivity
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    private val adapter = TaskListAdapter()
    private val viewModel: TaskListViewModel by viewModels()

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = result.data?.getSerializableExtra("task") as Task? ?: return@registerForActivityResult
            viewModel.create(task)
            viewModel.refresh()
        }

    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val newTask = result.data?.getSerializableExtra("task") as Task
            viewModel.update(newTask)
            viewModel.refresh()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_main)
        recyclerView.adapter = this.adapter
        val button = view.findViewById<ImageButton>(R.id.floatingActionButton)
            button.setOnClickListener {
                val intent = Intent(context, FormActivity::class.java)
                createTask.launch(intent)
        }
        lifecycleScope.launch {
            viewModel.tasksStateFlow.collect { newList ->
                adapter.submitList(newList)

            }
        }
        adapter.onClickDelete = { task ->
            viewModel.delete(task)
        }
        adapter.onClickEdit = { task ->
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
        val tmp = requireView().findViewById<TextView>(R.id.id)
        var avatar = view?.findViewById<ImageView>(R.id.avatar_image_view)
        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            tmp.text = "Bonjour " + userInfo.firstName + " " + userInfo.lastName
            avatar?.load(userInfo.avatar) {
                transformations(CircleCropTransformation())
            }
        }
    }
}