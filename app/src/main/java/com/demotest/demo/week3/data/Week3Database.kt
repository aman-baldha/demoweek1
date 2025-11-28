package com.demotest.demo.week3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 2,
    exportSchema = false
)
abstract class Week3Database : RoomDatabase() {
    abstract fun week3Dao(): Week3Dao

    companion object {
        @Volatile
        private var INSTANCE: Week3Database? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Example migration: adding a column or changing schema
                // For this demo, we just ensure the tables exist or are updated.
                // Since we are starting fresh, this is mostly illustrative.
            }
        }

        fun getDatabase(context: Context): Week3Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Week3Database::class.java,
                    "week3_database"
                )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration() // For dev convenience if migration fails
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
