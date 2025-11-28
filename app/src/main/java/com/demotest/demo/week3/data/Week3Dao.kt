package com.demotest.demo.week3.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface Week3Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithPosts(): Flow<List<UserWithPosts>>
    
    @Query("DELETE FROM users")
    suspend fun clearUsers()
    
    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}
