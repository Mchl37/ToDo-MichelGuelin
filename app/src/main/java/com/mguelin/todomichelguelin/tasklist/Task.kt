package com.mguelin.todomichelguelin.tasklist

import kotlinx.serialization.Serializable


@Serializable
data class Task (
    val id: String,
    val title: String,
    val description: String = ""
):java.io.Serializable{


}