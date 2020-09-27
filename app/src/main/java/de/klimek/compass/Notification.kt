package de.klimek.compass

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat

const val CHANNEL_ID = "COMPASS_CHANNEL"
const val NOTIFICATION_ID = 1

fun TileService.channel() = NotificationChannel(
    CHANNEL_ID,
    getString(R.string.channel_name),
    NotificationManager.IMPORTANCE_LOW
)

fun TileService.notification(): Notification = NotificationCompat.Builder(this, CHANNEL_ID)
    .setSmallIcon(R.drawable.ic_arrow)
    .setAutoCancel(true)
    .build()
