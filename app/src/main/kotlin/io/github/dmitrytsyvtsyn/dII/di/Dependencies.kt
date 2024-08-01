package io.github.dmitrytsyvtsyn.dII.di

import android.content.Context
import androidx.room.Room
import io.github.dmitrytsyvtsyn.core.di.DI
import io.github.dmitrytsyvtsyn.dII.AppDatabase

fun DI.initAppDependencies(applicationContext: Context) {
    singleton {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).build()
    }
    factory { instance<AppDatabase>().postDao() }
}