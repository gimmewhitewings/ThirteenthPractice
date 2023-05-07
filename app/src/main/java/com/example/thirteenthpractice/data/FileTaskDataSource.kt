package com.example.thirteenthpractice.data

import android.content.Context

class FileTaskDataSource(private val context: Context) : TaskDataSource {

    // file for storing tasks
    private val tasksFile = "tasks.txt"

    override fun getTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val file = context.getFileStreamPath(tasksFile)
        if (file.exists()) {
            context.openFileInput(tasksFile).bufferedReader().forEachLine {
                val (description, isDone) = it.split(":")
                tasks.add(Task(description, isDone.toBoolean()))
            }
        }

        return tasks
    }

    override fun addTask(task: Task) {
        context.openFileOutput(tasksFile, Context.MODE_APPEND).bufferedWriter().use {
            it.write("$task\n")
        }
    }

    override fun deleteTask(task: Task) {
        val tasks = getTasks()
        context.openFileOutput(tasksFile, Context.MODE_PRIVATE).bufferedWriter().use {
            tasks.forEach { t ->
                if (t.description != task.description) {
                    it.write("$t\n")
                }
            }
        }
    }

    override fun updateTask(task: Task) {
        val tasks = getTasks()
        context.openFileOutput(tasksFile, Context.MODE_PRIVATE).bufferedWriter().use {
            tasks.forEach { t ->
                if (t.description == task.description) {
                    it.write("${task.toString()}\n")
                } else {
                    it.write("${t.toString()}\n")
                }
            }
        }
    }
}