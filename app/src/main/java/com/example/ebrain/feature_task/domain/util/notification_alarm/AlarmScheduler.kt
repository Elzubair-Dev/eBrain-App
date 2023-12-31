package com.example.ebrain.feature_task.domain.util.notification_alarm

interface AlarmScheduler {
    fun schedule(item: TaskAlarmItems)
    fun cancel(item: TaskAlarmItems)
}