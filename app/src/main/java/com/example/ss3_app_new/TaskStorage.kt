package com.example.ss3_app_new

object TaskStorage {
    private val taskList = mutableListOf<Task>()

    fun addTask(task: Task) {
        taskList.add(task)
    }

    fun getTasksSortedByDate(): List<Task> {
        return taskList.sortedBy { it.date }
    }
}
