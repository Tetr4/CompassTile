package de.klimek.compass

import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.Icon
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.service.quicksettings.Tile
import android.util.Log
import de.klimek.compass.tile.IconFactory
import de.klimek.compass.tile.label
import de.klimek.compass.tile.update

private val TAG = TileService::class.java.simpleName
private const val SENSOR_DELAY = SensorManager.SENSOR_DELAY_UI

class TileService : android.service.quicksettings.TileService(), SensorEventListener {

    private val sensorManager
        get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val notificationManager
        get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val sensor by lazy {
        // TYPE_ROTATION_VECTOR is a fusion of gyro, accelerometer and compass.
        // This is more accurate and responsive than TYPE_MAGNETIC_FIELD.
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    private val isSupported
        get() = sensor != null

    private lateinit var iconFactory: IconFactory

    override fun onCreate() {
        Log.i(TAG, "Create")
        iconFactory = IconFactory(this, R.drawable.ic_arrow)
        notificationManager.createNotificationChannel(channel())
        if (!isSupported) {
            qsTile.update { state = Tile.STATE_UNAVAILABLE }
        }
    }

    override fun onStartListening() {
        if (isSupported && qsTile.state == Tile.STATE_ACTIVE) {
            startCompass()
        }
    }

    override fun onStopListening() {
        if (isSupported && qsTile.state == Tile.STATE_ACTIVE) {
            stopCompass()
        }
    }

    override fun onClick() {
        if (qsTile.state == Tile.STATE_ACTIVE) {
            setInactive()
        } else {
            setActive()
        }
    }

    private fun setActive() {
        qsTile.update { state = Tile.STATE_ACTIVE }
        startCompass()
    }

    private fun setInactive() {
        qsTile.update {
            state = Tile.STATE_INACTIVE
            icon = Icon.createWithResource(this@TileService, R.drawable.ic_arrow_disabled)
            label = getString(R.string.app_name)
        }
        stopCompass()
    }

    private fun startCompass() {
        Log.i(TAG, "Start")
        startForeground(NOTIFICATION_ID, notification())
        sensorManager.registerListener(this, sensor, SENSOR_DELAY)
    }

    private fun stopCompass() {
        Log.i(TAG, "Stop")
        sensorManager.unregisterListener(this)
        stopForeground(true)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) = Unit
    override fun onSensorChanged(event: SensorEvent) {
        val degrees = event.getAzimuthDegrees()
        Log.v(TAG, degrees.toString())
        qsTile.update {
            label = label(degrees)
            icon = iconFactory.build(degrees)
        }
    }
}
