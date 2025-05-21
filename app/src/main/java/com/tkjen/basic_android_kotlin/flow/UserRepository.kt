package com.tkjen.basic_android_kotlin.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository {

    fun getUsers(): Flow<List<User>> = flow {
        delay(1000) // Giả lập API
        emit(
            listOf(
                User(1, "Alice"),
                User(2, "Bob"),
                User(3, "Charlie")
            )
        )
    }
}