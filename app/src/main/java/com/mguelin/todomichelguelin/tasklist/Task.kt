package com.mguelin.todomichelguelin.tasklist

data class Task (
    val id: String,
    val title: String,
    val description: String = "Description"
)