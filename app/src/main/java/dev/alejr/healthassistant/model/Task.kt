package dev.alejr.healthassistant.model

data class Task(
    val description: String = "",
    val date: String = "",
    val completed: Boolean = false,
    val id: Number = -1,
    val dateString: String = ""
)