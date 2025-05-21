package com.tkjen.basic_android_kotlin.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {
    private val repository = UserRepository()

    // 🔥 StateFlow - giữ state UI
    private val _usersState = MutableStateFlow<List<User>>(emptyList())
    val usersState: StateFlow<List<User>> = _usersState

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // 🔥 SharedFlow - phát sự kiện 1 lần (toast, error)
    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> = _eventFlow

    fun loadUsers() {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.getUsers() // ❄️ Cold Flow
                    .collect { users ->
                        _usersState.value = users
                    }
            } catch (e: Exception) {
                _eventFlow.emit("Lỗi khi tải danh sách")
            } finally {
                _loading.value = false
            }
        }
    }

    fun onUserClick(user: User) {
        viewModelScope.launch {
            _eventFlow.emit("Bạn đã chọn: ${user.name}")
        }
    }
}