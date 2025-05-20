package com.tkjen.basic_android_kotlin.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // Nếu trùng id, bỏ qua
    suspend fun addUser(user: User) // suspend cho Coroutines

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

        @Query("SELECT * FROM user_table ORDER BY id ASC")
        fun getAllUsers(): Flow<List<User>> // Sử dụng Flow để quan sát thay đổi (hoặc LiveData)

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserById(userId: Int): Flow<User?>
}