package io.github.dmitrytsyvtsyn.core.di

import kotlin.reflect.KClass

interface Provider<T> {
    fun provide() : T
}

object DI {

    val map: MutableMap<KClass<*>, Provider<*>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> instance(): T {
        return map[T::class]?.provide() as T
    }

    inline fun <reified T : Any> factory(crossinline dependencyProducer: () -> T) {
        map[T::class] = object : Provider<T> {
            override fun provide(): T {
                return dependencyProducer.invoke()
            }
        }
    }

    inline fun <reified T : Any> singleton(crossinline dependencyProducer: () -> T) {
        map[T::class] = object : Provider<T> {
            private var _dependency: T? = null

            override fun provide(): T {
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
