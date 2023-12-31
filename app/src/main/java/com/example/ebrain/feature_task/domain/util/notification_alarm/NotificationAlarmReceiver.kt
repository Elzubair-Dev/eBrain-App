package com.example.ebrain.feature_task.domain.util.notification_alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class NotificationAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("TITLE") ?: return
        val id = intent.getIntExtra("ID", 0)

        val service = TaskNotificationService(context!!)
        service.showNotification(title = title, id = id)

    }
}