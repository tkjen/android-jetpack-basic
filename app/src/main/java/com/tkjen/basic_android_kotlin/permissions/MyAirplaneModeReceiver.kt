package com.tkjen.basic_android_kotlin.permissions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyAirplaneModeReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeOn = intent.getBooleanExtra("state", false)
            val message = if (isAirplaneModeOn) {
                "Chế độ máy bay BẬT"
            } else {
                "Chế độ máy bay TẮT"
            }
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            Log.d("AirplaneModeReceiver", message)
        }
    }


}