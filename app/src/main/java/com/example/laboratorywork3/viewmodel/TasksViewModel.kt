package com.example.laboratorywork3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboratorywork3.model.Priority
import com.example.laboratorywork3.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TasksViewModel : ViewModel() {

    private var nextId = 3

    private val _tasks = MutableStateFlow(
        listOf(
            Task(1, "Здати лабораторну", "Завантажити проєкт і звіт", Priority.HIGH),
            Task(2, "Купити продукти", "Молоко, хліб, яйця", Priority.LOW)
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun addTask(title: String, description: String, priority: Priority) {
        _tasks.update { it + Task(nextId++, title.trim(), description.trim(), priority) }
    }

    fun updateTask(id: Int, title: String, description: String, priority: Priority) {
        _tasks.update { list ->
            list.map {
                if (it.id == id) it.copy(title = title.trim(), description = description.trim(), priority = priority)
                else it
            }
        }
    }

    fun toggleDone(id: Int) {
        _tasks.update { list -> list.map { if (it.id == id) it.copy(isDone = !it.isDone) else it } }
    }

    fun deleteTask(id: Int) {
        _tasks.update { list -> list.filterNot { it.id == id } }
    }
}
