package com.tkjen.basic_android_kotlin.viewmodels.stateFlow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModelWithStateFlow:ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState :StateFlow<UiState> = _uiState.asStateFlow()
    fun loadData() {
        viewModelScope.launch {
            Log.d("MyViewModelWithStateFlow", "Loading data...")
            _uiState.value = _uiState.value.copy(isLoading = true, message = "Đang tải...")
            delay(2000) // Giả lập tải dữ liệu
            _uiState.value = _uiState.value.copy(isLoading = false, message = "Dữ liệu đã tải xong!")
            Log.d("MyViewModelWithStateFlow", "Data loaded!")
        }
    }
}