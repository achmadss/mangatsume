package com.achmadss.mangatsume.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.achmadss.mangatsume.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UpdateService : Service(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private var updateItemCount = 0
    private var counter = 0
    private var updateSuccessList = mutableListOf<String>()
    private var updateFailedList = mutableListOf<String>()

    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        reset()
        createNotificationChannel()
        notificationBuilder = createNotification()

        val stopIntent = Intent(COUNTER_UPDATE_ACTION_STOP)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE
        )
        notificationBuilder.apply {
            addAction(R.drawable.ic_notification, "STOP", pendingIntent)
        }

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        startDatabaseOperations()
        return START_REDELIVER_INTENT
    }

    private fun reset() {
        updateItemCount = 0
        counter = 0
        updateSuccessList = mutableListOf()
        updateFailedList = mutableListOf()
    }

    private fun startDatabaseOperations() {
        launch {
            val mangaTitles = mutableListOf(
                "One Piece",
                "Naruto",
                "Attack on Titan",
                "Dragon Ball",
                "Death Note",
                "My Hero Academia",
                "Fullmetal Alchemist",
                "Tokyo Ghoul",
                "Bleach",
                "Demon Slayer: Kimetsu no Yaiba"
            )
            updateItemCount = mangaTitles.size
            repeat(updateItemCount) {
                delay(500)
                counter = it
                val random = (0..1).random()
                if (random == 1) {
                    updateSuccessList.add(mangaTitles[it])
                } else {
                    updateFailedList.add(mangaTitles[it])
                }
                updateNotificationProgress(counter, mangaTitles[it])
            }
            onUpdateFinish(updateSuccessList, updateFailedList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this@UpdateService,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
    }

    private fun onUpdateFinish(
        updateSuccess: List<String>,
        updateFailed: List<String>,
    ) {
        stopForeground(STOP_FOREGROUND_REMOVE)
        Log.e("ASD", "success: $updateSuccess")
        Log.e("ASD", "failed: $updateFailed")
        checkPermission()
        NotificationManagerCompat.from(this).apply {
            updateSuccess.forEachIndexed { index, data ->
                val notification = NotificationCompat.Builder(this@UpdateService, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentTitle(data)
                notify(NOTIFICATION_ID.plus(index + 1), notification.build())
            }
            val failedNotification = NotificationCompat.Builder(this@UpdateService, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            failedNotification.apply {
                if (updateFailed.isNotEmpty()) {
                    setContentTitle("${updateFailed.size} failed to update")
                } else {
                    setContentTitle("${updateSuccess.size} manga updated")
                }
            }
            notify(NOTIFICATION_ID, failedNotification.build())
        }
        isRunning = false
    }

    private fun updateNotificationProgress(progress: Int, title: String) {
        NotificationManagerCompat.from(this).apply {
            notificationBuilder.apply {
                setContentText(title)
                setProgress(updateItemCount, progress, false)
            }
            checkPermission()
            notify(NOTIFICATION_ID, notificationBuilder.build())
            sendStartCounterBroadcast(progress + 1)
        }
    }

    private fun sendStartCounterBroadcast(progressValue: Int) {
        val intent = Intent(COUNTER_UPDATE_ACTION_START).apply {
            putExtra(COUNTER_VALUE_EXTRA, progressValue)
        }
        sendBroadcast(intent)
    }

    private fun createNotification(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Updating library...")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(updateItemCount, 0, false)
            .setSound(null)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "notification_channel_id"
        private const val CHANNEL_NAME = "Update Service Channel"
        private const val NOTIFICATION_ID = 10000001
        const val COUNTER_UPDATE_ACTION_START = "com.example.app.COUNTER_UPDATE_ACTION_START"
        const val COUNTER_UPDATE_ACTION_STOP = "com.example.app.COUNTER_UPDATE_ACTION_STOP"
        const val COUNTER_VALUE_EXTRA = "update_counter_value"

        private var isRunning = false

        fun startService(
            context: Context, // use ApplicationContext
            onAlreadyRunning: () -> Unit,
        ) {
            if (isRunning) {
                onAlreadyRunning()
                return
            }
            val intent = Intent(context, UpdateService::class.java)
            Log.e("ASD", "start")
            context.startForegroundService(intent)
            isRunning = true
        }

        fun stopService(
            context: Context, // use ApplicationContext
        ) {
            if (isRunning) {
                val intent = Intent(context, UpdateService::class.java)
                Log.e("ASD", "stop")
                context.stopService(intent)
                isRunning = false
            }
        }
    }

}