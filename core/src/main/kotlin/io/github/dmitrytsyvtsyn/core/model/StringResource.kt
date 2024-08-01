package io.github.dmitrytsyvtsyn.core.model

@JvmInline
value class StringResource(val value: Int) {
    val isEmpty: Boolean
        get() = value < 0

    val isNotEmpty: Boolean
        get() = !isEmpty

    companion object {
        val Empty = StringResource(-1)
    }
}