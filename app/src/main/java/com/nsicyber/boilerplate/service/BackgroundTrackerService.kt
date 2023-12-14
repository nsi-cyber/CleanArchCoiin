package com.nsicyber.boilerplate.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.core.app.NotificationCompat
import com.nsicyber.boilerplate.R
import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.data.remote.entity.CoinEntity
import com.nsicyber.boilerplate.data.repository.UserStore
import com.nsicyber.boilerplate.domain.model.toCoinEntity
import com.nsicyber.boilerplate.domain.useCase.coin.GetCoinDetailUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.AddToFavoriteUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.GetUserFavoritesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BackgroundTrackerService : Service() {

    private val handler = Handler()
    private val runnableMap = mutableMapOf<String, Runnable>()

    @Inject
    lateinit var getCoinDetailUseCase: GetCoinDetailUseCase

    @Inject

    lateinit var addToFavoriteUseCase: AddToFavoriteUseCase

    @Inject
    lateinit var getUserFavoritesUseCase: GetUserFavoritesUseCase

    companion object {
        private const val TAG = "BackgroundTrackerService"
        private const val NOTIFICATION_CHANNEL_ID = "my_service"
        private const val NOTIFICATION_CHANNEL_NAME = "Coiin Tracker"
        private const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Tracker service created.")
        val store = UserStore(this)
        GlobalScope.launch(Dispatchers.Main) {
            store.getLastUser.collect {

                val notification = createNotification()
                startForeground(NOTIFICATION_ID, notification)

                if (it != null) {
                    GlobalScope.launch(Dispatchers.IO) {


                        getUserFavoritesUseCase(it).onEach { result ->
                            when (result) {
                                is Resource.Success -> {
                                    result.data?.forEach { model ->
                                        startBackgroundTask(model)
                                    }
                                }

                                else -> {}
                            }
                        }
                    }
                }
            }
        }


    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun addRunnable(id: String, model: CoinEntity) {
        val store = UserStore(this)
        GlobalScope.launch(Dispatchers.Main) {

            store.getLastUser.collect { lastUser ->

                val runnable = object : Runnable {
                    override fun run() {
                        GlobalScope.launch(Dispatchers.IO) {
                            try {
                                val notificationService = NotificationService(applicationContext)

                                getCoinDetailUseCase(model.id!!).onEach { resultModel ->
                                    when (resultModel) {
                                        is Resource.Success -> {
                                            addToFavoriteUseCase(
                                                lastUser,
                                                resultModel.data?.toCoinEntity()
                                            ).onEach { result ->
                                                when (result) {
                                                    is Resource.Success -> {


                                                        if (resultModel.data?.currentPrice != model.currentPrice) {
                                                            if ((resultModel.data?.currentPrice
                                                                    ?: 0).toDouble() > (model.currentPrice
                                                                    ?: 0).toDouble()
                                                            )
                                                                notificationService.showNotification(
                                                                    model = resultModel.data,
                                                                    isUp = true
                                                                )
                                                            else
                                                                notificationService.showNotification(
                                                                    model = resultModel.data,
                                                                    isUp = false
                                                                )
                                                        }
                                                    }

                                                    else -> {}
                                                }
                                            }


                                        }

                                        else -> {}
                                    }
                                }

                            } catch (e: Exception) {
                                Log.e(TAG, "API call failed: addRunnable ${e.message}")
                            }
                        }
                        handler.postDelayed(
                            this,
                            3600000
                        )
                    }
                }

                handler.postDelayed(runnable, 0)
                runnableMap[id] = runnable
            }
        }
    }

    fun cancelRunnable(id: String) {
        runnableMap[id]?.let {
            handler.removeCallbacks(it)
            runnableMap.remove(id)
        }
    }

    private fun startBackgroundTask(model: CoinEntity?) {
        model?.id?.let { addRunnable(it, model) }
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Coiin Tracker")
            .setContentText("Service working...")
            .setSmallIcon(R.drawable.ic_logo)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}

