package com.mguelin.todomichelguelin.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.mguelin.todomichelguelin.R
import com.mguelin.todomichelguelin.tasklist.Task
import java.util.*

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val button = findViewById<Button>(R.id.buttonForm)
        val titleText = findViewById<EditText>(R.id.newTaskTitle)
        val descriptionText = findViewById<EditText>(R.id.newTaskDescription)
        val taskEdit = intent.getSerializableExtra("task") as? Task
        descriptionText.setText(taskEdit?.description)
        titleText.setText(taskEdit?.title)

        button.setOnClickListener {
            val newTask = Task(id = taskEdit?.id ?: UUID.randomUUID().toString(), title = titleText.text.toString(),description = descriptionText.text.toString())
            intent.putExtra("task", newTask)
            setResult(RESULT_OK, intent)
            finish();
        }
    }
}