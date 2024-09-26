package io.github.dmitrytsyvtsyn.core.di

import kotlin.reflect.KClass

interface Factory<T> {
    fun create() : T
}

object DI {

    val map: MutableMap<KClass<*>, Factory<*>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> instance(): T {
        return map[T::class]?.create() as T
    }

    inline fun <reified T : Any> factory(crossinline dependencyProducer: () -> T) {
        map[T::class] = object : Factory<T> {
            override fun create(): T {
                return dependencyProducer.invoke()
            }
        }
    }

    inline fun <reified T : Any> singleton(crossinline dependencyProducer: () -> T) {
        map[T::class] = object : Factory<T> {
            @Volatile
            private var _dependency: T? = null

            override fun create(): T {
                _dependency?.let { return it }
                synchronized(this) {
                    _dependency?.let { return it }
                    val dependency = dependencyProducer.invoke()
                    _dependency = dependency
                    return dependency
                }
            }
        }
    }

}
