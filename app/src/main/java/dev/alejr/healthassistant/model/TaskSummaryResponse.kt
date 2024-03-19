package dev.alejr.healthassistant.model

data class TaskSummaryResponse(
    val overdue: Array<Task> = emptyArray(),
    val open: Array<Task> = emptyArray(),
    val completed: Array<Task> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskSummaryResponse

        if (!overdue.contentEquals(other.overdue)) return false
        if (!open.contentEquals(other.open)) return false
        return completed.contentEquals(other.completed)
    }

    override fun hashCode(): Int {
        var result = overdue.contentHashCode()
        result = 31 * result + open.contentHashCode()
        result = 31 * result + completed.contentHashCode()
        return result
    }
}