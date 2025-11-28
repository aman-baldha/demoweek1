package com.demotest.demo.week3.data

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithPosts(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val posts: List<PostEntity>
)
