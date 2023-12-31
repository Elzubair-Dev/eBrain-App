package com.example.ebrain.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ebrain.core.domain.util.Privacy
import com.example.ebrain.feature_task.domain.util.Interval
import com.example.ebrain.feature_task.domain.util.Priority
import java.time.LocalDateTime

@Entity
data class Task(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val priority: Priority,
    val date: LocalDateTime,
    val isRepeatable: Boolean,
    val interval: Interval?,
    val intervalValue: Long?,
    val privacy: Privacy
)
class InvalidTaskException(message: String): Exception(message)