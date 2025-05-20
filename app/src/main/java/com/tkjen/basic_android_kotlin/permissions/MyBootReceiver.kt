package com.tkjen.basic_android_kotlin.permissions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("MyBootReceiver", "Thiết bị đã khởi động xong!")
            Toast.makeText(context, "Thiết bị khởi động xong!", Toast.LENGTH_LONG).show()

        }
    }
}