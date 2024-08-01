package io.github.dmitrytsyvtsyn.dII

import android.app.Application
import io.github.dmitrytsyvtsyn.core.di.DI
import io.github.dmitrytsyvtsyn.core.di.initCoreDependencies
import io.github.dmitrytsyvtsyn.dII.di.initAppDependencies

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DI.initAppDependencies(applicationContext)
        DI.initCoreDependencies()
    }

}