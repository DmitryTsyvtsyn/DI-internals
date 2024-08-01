package io.github.dmitrytsyvtsyn.core.di

import io.github.dmitrytsyvtsyn.core.usecases.PostDeleteUseCase
import io.github.dmitrytsyvtsyn.core.usecases.PostFetchAllUseCase
import io.github.dmitrytsyvtsyn.core.usecases.PostFetchByIdUseCase
import io.github.dmitrytsyvtsyn.core.usecases.PostInsertUseCase

fun DI.initCoreDependencies() {
    factory {
        PostInsertUseCase(instance())
    }
    factory {
        PostDeleteUseCase(instance())
    }
    factory {
        PostFetchAllUseCase(instance())
    }
    factory {
        PostFetchByIdUseCase(instance())
    }
}