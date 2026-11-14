package com.example.laboratorywork3.model

enum class Priority(val label: String) {
    LOW("Низький"),
    MEDIUM("Середній"),
    HIGH("Високий")
}

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val isDone: Boolean = false
)
