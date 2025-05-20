package com.tkjen.basic_android_kotlin.work

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.unit.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.tkjen.basic_android_kotlin.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonUpload.setOnClickListener {
            val fakeImageUri = binding.editImageUri.text.toString()

            if (fakeImageUri.isNotBlank()) {
                enqueueUploadWork(fakeImageUri)
            } else {
                Toast.makeText(this, "Vui lòng nhập image URI!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enqueueUploadWork(imageUri: String) {
        // 1. Đặt điều kiện (constraints)
        val constraints = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // cần mạng
            .build()

        // 2. Dữ liệu đầu vào
        val inputData = androidx.work.Data.Builder()
            .putString("KEY_IMAGE_URI", imageUri)
            .build()

        // 3. Tạo WorkRequest
        val uploadRequest = OneTimeWorkRequestBuilder<MyUploadWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .addTag("image_upload_work")
            .build()

        // 4. Đưa vào hàng đợi
        WorkManager.getInstance(applicationContext).enqueue(uploadRequest)

        // 5. Lắng nghe trạng thái (tùy chọn)
        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this) { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.ENQUEUED -> Log.d("WorkManager", "Đã xếp hàng upload.")
                        WorkInfo.State.RUNNING -> Log.d("WorkManager", "Đang upload...")
                        WorkInfo.State.SUCCEEDED -> Log.d("WorkManager", "Upload thành công!")
                        WorkInfo.State.FAILED -> Log.d("WorkManager", "Upload thất bại.")
                        WorkInfo.State.BLOCKED -> Log.d("WorkManager", "Chưa đủ điều kiện để chạy.")
                        WorkInfo.State.CANCELLED -> Log.d("WorkManager", "Upload đã bị hủy.")
                    }
                }
            }

        Log.d("WorkManager", "Đã enqueue công việc upload.")
    }
}