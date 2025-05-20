package com.tkjen.basic_android_kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    // Dữ liệu được quản lý bởi ViewModel
    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int> get() = _counter // LiveData chỉ cho phép đọc từ bên ngoài

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        _counter.value = 0
        _isLoading.value = false
        Log.d("MyViewModel", "ViewModel initialized")
    }

    fun incrementCounter() {
        _counter.value = (_counter.value ?: 0) + 1
    }

    fun fetchDataFromServer() {
        // Sử dụng viewModelScope để chạy coroutine
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("MyViewModel", "Fetching data...")
            delay(2000) // Giả lập việc lấy dữ liệu từ server
            // Giả sử dữ liệu mới là 100
            _counter.value = 100
            _isLoading.value = false
            Log.d("MyViewModel", "Data fetched!")
        }
    }

    // Được gọi khi ViewModel không còn được sử dụng và sắp bị hủy
    override fun onCleared() {
        super.onCleared()
        Log.d("MyViewModel", "ViewModel onCleared called. Cleaning up resources...")
        // Hủy bỏ các coroutine, giải phóng tài nguyên ở đây nếu cần
    }
}