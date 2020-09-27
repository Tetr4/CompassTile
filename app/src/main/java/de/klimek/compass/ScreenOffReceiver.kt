package de.klimek.compass

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class ScreenOffReceiver(
    private val onScreenOff: () -> Unit
) : BroadcastReceiver() {
    val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
    override fun onReceive(context: Context, intent: Intent) {
        onScreenOff()
    }
}