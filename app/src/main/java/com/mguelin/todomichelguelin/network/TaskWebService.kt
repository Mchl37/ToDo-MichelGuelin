package com.mguelin.todomichelguelin.network

import com.mguelin.todomichelguelin.tasklist.Task
import retrofit2.Response
import retrofit2.http.*

interface TaskWebService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>
    @POST("tasks")
    suspend fun create(@Body task: Task): Response<Task>
    @PATCH("tasks/{id}")
    suspend fun update(@Body task: Task, @Path("id") id: String = task.id): Response<Task>
    @DELETE("tasks/{id}")
    suspend fun delete(@Path("id") id: String): Response<Unit>
}