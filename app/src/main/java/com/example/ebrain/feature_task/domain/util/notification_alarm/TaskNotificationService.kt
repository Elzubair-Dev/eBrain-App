package com.example.ebrain.feature_task.domain.util.notification_alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.ebrain.R
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class TaskNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object{
        const val TASK_CHANNEL_ID = "TASK_ID"
    }

    fun showNotification(
        id: Int,
        title: String
    ){
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            id,
            Intent(context, NotificationAlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, TASK_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText("Time to \"$title\"")
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}