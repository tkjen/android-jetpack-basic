package com.tkjen.basic_android_kotlin.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class MyUploadWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "MyUploadWorker"
    }

    override suspend fun doWork(): Result {
        // Lấy dữ liệu đầu vào nếu có
        val inputDataString = inputData.getString("KEY_IMAGE_URI")
        Log.d(TAG, "Bắt đầu công việc upload: $inputDataString")

        try {
            // Giả lập công việc upload (ví dụ: gọi API, ghi file)
            for (i in 1..5) {
                Log.d(TAG, "Đang upload... $i/5")
                delay(1000)
            }
            Log.d(TAG, "Upload thành công!")
            return Result.success() // Báo thành công
        } catch (e: Exception) {
            Log.e(TAG, "Upload thất bại", e)
            if (runAttemptCount < 3) { // Thử lại tối đa 2 lần nữa nếu thất bại
                Log.d(TAG, "Thử lại lần ${runAttemptCount + 1}")
                return Result.retry()
            }
            return Result.failure() // Báo thất bại sau khi đã thử lại
        }
    }
}