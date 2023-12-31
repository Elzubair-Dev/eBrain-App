package com.example.ebrain.feature_task.domain.util.notification_alarm

import java.time.LocalDateTime

data class TaskAlarmItems(
    val id: Int,
    val time: LocalDateTime,
    val intervalValue: Long? = null,
    val title: String
)
