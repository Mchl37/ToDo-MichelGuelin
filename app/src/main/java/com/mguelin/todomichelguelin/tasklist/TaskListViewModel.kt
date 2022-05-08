package com.mguelin.todomichelguelin.tasklist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mguelin.todomichelguelin.network.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val webService = Api.taskWebService
    private val _tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())
    public val tasksStateFlow: StateFlow<List<Task>> = _tasksStateFlow.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            val response = webService.getTasks()
            if (response.isSuccessful) {
                Log.e("Network", "Error: ${response.message()}")
            }
            val fetchedTasks = response.body()!!
            _tasksStateFlow.value = fetchedTasks
        }
    }

    fun create(task: Task) {
        viewModelScope.launch {
            val response = webService.create(task)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }

            val createdTask = response.body()!!
            _tasksStateFlow.value = _tasksStateFlow.value + createdTask
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            val response = webService.update(task)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }

            val updatedTask = response.body()!!
            _tasksStateFlow.value = _tasksStateFlow.value - task + updatedTask
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            val response = webService.delete(task.id)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }
            _tasksStateFlow.value = _tasksStateFlow.value - task
        }
    }
}