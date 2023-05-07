package com.example.thirteenthpractice.data

import com.example.thirteenthpractice.data.Task

interface TaskDataSource {
    fun getTasks(): List<Task>
    fun addTask(task: Task)
    fun deleteTask(task: Task)
    fun updateTask(task: Task)
}