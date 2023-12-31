package com.example.ebrain.core

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.ebrain.feature_task.domain.util.notification_alarm.TaskNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EBrain:Application(){
    override fun onCreate() {
        super.onCreate()
        createTaskNotificationChannel()
    }
    private fun createTaskNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                TaskNotificationService.TASK_CHANNEL_ID,
                "Task",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = null
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}