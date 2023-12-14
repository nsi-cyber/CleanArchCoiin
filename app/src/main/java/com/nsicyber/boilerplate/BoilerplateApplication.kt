package com.nsicyber.boilerplate

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.nsicyber.boilerplate.service.BackgroundTrackerService
import com.nsicyber.boilerplate.service.NotificationService
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BoilerplateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        createNotificationChannel()
        val serviceIntent = Intent(this, BackgroundTrackerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, serviceIntent)
        } else {
            this.startService(serviceIntent)
        }
    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.TRACKER_CHANNEL,
                "Tracker",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used for the notifications"

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }}