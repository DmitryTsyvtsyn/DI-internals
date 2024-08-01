package io.github.dmitrytsyvtsyn.deps_list.viewmodel

import io.github.dmitrytsyvtsyn.core.model.PostModel

internal sealed interface PostListEvent {
    data object FetchAll : PostListEvent
    class Delete(val model: PostModel) : PostListEvent
    class View(val model: PostModel) : PostListEvent
    data object Add : PostListEvent
}