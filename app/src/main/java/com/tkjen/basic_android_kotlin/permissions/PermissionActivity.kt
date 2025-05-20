package com.tkjen.basic_android_kotlin.permissions

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tkjen.basic_android_kotlin.databinding.ActivityPermissionBinding

class PermissionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPermissionBinding
     private lateinit var airplaneModeReceiver: MyAirplaneModeReceiver
     private lateinit var filter: IntentFilter

    // Add notification permission launcher
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            // Start service after permission is granted
            startForegroundService()
        } else {
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
        updatePermissionStatus()
    }

    // Existing permission launchers...
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
        updatePermissionStatus()
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
        updatePermissionStatus()
    }

    // Modified multiple permissions launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        updatePermissionStatus()

        // Check if all required permissions are granted
        if (permissions.all { it.value }) {
            Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
            startForegroundService()
        } else {
            Toast.makeText(this, "Some permissions were denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize permission status display
        updatePermissionStatus()

        // Set up button click listeners
        binding.btnCameraPermission.setOnClickListener {
            requestCameraPermission()
        }

        binding.btnLocationPermission.setOnClickListener {
            requestLocationPermission()
        }

        // Add button for notification permission and service
        binding.btnNotificationPermission.setOnClickListener {
            requestNotificationPermission()
        }

        binding.requestPermissionsButton.setOnClickListener {
            checkAndRequestPermissions()
        }

        airplaneModeReceiver = MyAirplaneModeReceiver()
        filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
    }

    private fun updatePermissionStatus() {
        val cameraGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val locationGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        // Check notification permission based on Android version
        val notificationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Always true for Android < 13
        }

        binding.textCameraPermissionStatus.text = "Camera Permission: " +
                if (cameraGranted) "Granted" else "Not Granted"

        binding.textLocationPermissionStatus.text = "Location Permission: " +
                if (locationGranted) "Granted" else "Not Granted"

        // Add notification status text
        binding.textNotificationPermissionStatus.text = "Notification Permission: " +
                if (notificationGranted) "Granted" else "Not Granted"
    }

    // New method to request notification permission
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                Toast.makeText(this, "Notification permission already granted", Toast.LENGTH_SHORT).show()
                startForegroundService()
            }
        } else {
            Toast.makeText(this, "Notification permission not required for this Android version", Toast.LENGTH_SHORT).show()
            startForegroundService()
        }
    }

    // Method to start the foreground service
    private fun startForegroundService() {
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        Toast.makeText(this, "Foreground service started", Toast.LENGTH_SHORT).show()
    }

    // Existing methods...
    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            Toast.makeText(this, "Camera permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            Toast.makeText(this, "Location permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Add notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            Toast.makeText(this, "All permissions already granted", Toast.LENGTH_SHORT).show()
            startForegroundService()
        }
    }

    override fun onResume() {
        super.onResume()
             registerReceiver(airplaneModeReceiver, filter) // Đăng ký động
    Log.d("MyActivity", "AirplaneModeReceiver registered")
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(airplaneModeReceiver) // Hủy đăng ký để tránh leak
        Log.d("MyActivity", "AirplaneModeReceiver unregistered")
    }
}