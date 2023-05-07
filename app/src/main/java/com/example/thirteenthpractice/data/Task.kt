package com.example.thirteenthpractice.data

data class Task(
    val description: String,
    var isDone: Boolean = false
) {
    override fun toString(): String {
        return "$description:$isDone"
    }
}