package com.tkjen.basic_android_kotlin.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao: UserDao
    val allUsers: LiveData<List<User>>

    init {
        val db = AppDatabase.getDatabase(application)
        userDao = db.userDao()
        // Chuyển Flow sang LiveData cho dễ quan sát trong UI
        allUsers = userDao.getAllUsers().asLiveData()

    }

    // Hàm thêm user (chạy trong Coroutine scope của ViewModel)
    fun addUser(user: User) = viewModelScope.launch {
        Log.d("UserViewModel", "Adding user: $user")
        userDao.addUser(user)
    }

    // Hàm cập nhật user
    fun updateUser(user: User) = viewModelScope.launch {
        userDao.updateUser(user)
    }

    // Hàm xóa user
    fun deleteUser(user: User) = viewModelScope.launch {
        userDao.deleteUser(user)
    }

    // Hàm xóa tất cả user
    fun deleteAllUsers() = viewModelScope.launch {
        userDao.deleteAllUsers()
    }
}

