package com.demotest.demo.week3.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Week3Repository(private val week3Dao: Week3Dao) {

    val usersWithPosts: Flow<List<UserWithPosts>> = week3Dao.getUsersWithPosts()

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            // Simulate network fetch
            val users = listOf(
                UserEntity(1, "Alice", "alice@example.com"),
                UserEntity(2, "Bob", "bob@example.com")
            )
            val posts = listOf(
                PostEntity(101, 1, "Alice's First Post", "Hello World!"),
                PostEntity(102, 1, "Alice's Second Post", "Room is great."),
                PostEntity(201, 2, "Bob's Post", "Flow makes it easy.")
            )

            // Cache data in Room
            week3Dao.insertUsers(users)
            week3Dao.insertPosts(posts)
        }
    }
}
