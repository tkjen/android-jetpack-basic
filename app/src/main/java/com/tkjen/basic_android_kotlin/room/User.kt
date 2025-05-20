package com.tkjen.basic_android_kotlin.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User( @PrimaryKey(autoGenerate = true) // Khóa chính, tự động tăng
                 val id: Int = 0,
                 val firstName: String,
                 val lastName: String,
                 val age: Int)
