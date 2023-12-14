package com.nsicyber.boilerplate.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.nsicyber.boilerplate.R
import com.nsicyber.boilerplate.domain.model.CoinDetailModel
import com.nsicyber.boilerplate.presenter.MainActivity

class NotificationService(
    private val context: Context
) : Service() {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(model: CoinDetailModel?, isUp: Boolean?) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(context, TRACKER_CHANNEL)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Change in Price")
            .setContentText(
                if (isUp == true)
                    "${model?.name} 's price has increased! "
                else
                    "${model?.name} 's price has decreased! "


            )
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val TRACKER_CHANNEL = "tracker_channel"
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}