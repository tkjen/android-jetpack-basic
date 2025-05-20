package com.tkjen.basic_android_kotlin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [User::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao // Room sẽ tự sinh code cho hàm này

    companion object {
        // Singleton pattern để tránh tạo nhiều instance của database
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database" // Tên file database
                )
                    // .addMigrations(...) // Thêm migration nếu có thay đổi schema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

