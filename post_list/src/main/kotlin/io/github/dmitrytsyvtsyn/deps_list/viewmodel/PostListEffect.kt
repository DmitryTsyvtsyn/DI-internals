package io.github.dmitrytsyvtsyn.deps_list.viewmodel

import io.github.dmitrytsyvtsyn.core.model.PostModel

internal sealed interface PostListEffect {
    class View(val model: PostModel) : PostListEffect
    data object Add : PostListEffect
}