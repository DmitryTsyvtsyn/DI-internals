package io.github.dmitrytsyvtsyn.dII

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.dmitrytsyvtsyn.core.data.PostDao
import io.github.dmitrytsyvtsyn.core.data.PostEntity

@Database(entities = [ PostEntity::class ], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        const val NAME = "posts_database"
    }

}