package com.tkjen.basic_android_kotlin.permissions

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.tkjen.basic_android_kotlin.MainActivity
import com.tkjen.basic_android_kotlin.R

class MyForegroundService: Service() {
    private val TAG = "MyForegroundService"
    private val NOTIFICATION_CHANNEL_ID = "MyForegroundServiceChannel"
    private val NOTIFICATION_ID = 1

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")

        val notification = createNotification("Service đang chạy...")
        startForeground(NOTIFICATION_ID, notification) // Quan trọng!

        // Thực hiện công việc ở đây (ví dụ: trong một luồng khác hoặc coroutine)
        Thread {
            for (i in 1..60) {
                Log.d(TAG, "Foreground task running: $i")
                // Cập nhật notification nếu cần
                // updateNotification("Đang chạy: $i giây")
                Thread.sleep(1000)
            }
            stopSelf() // Tự dừng khi xong
        }.start()

        return START_NOT_STICKY
    }

    private fun createNotification(contentText: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java) // Activity sẽ mở khi nhấn noti
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)


        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText(contentText)
            .setSmallIcon(R.mipmap.ic_launcher) // Thay bằng icon của bạn
            .setContentIntent(pendingIntent) // Cho phép nhấn vào noti để mở app
            .build()
    }

    // Cần cho Android 8.0 (API 26) trở lên
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    // (Tùy chọn) Cập nhật nội dung notification
    private fun updateNotification(newText: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = createNotification(newText)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
    // Khởi động Foreground Service (từ API 26 trở lên nên dùng startForegroundService)
// val serviceIntent = Intent(this, MyForegroundService::class.java)
// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//     startForegroundService(serviceIntent)
// } else {
//     startService(serviceIntent)
// }
}