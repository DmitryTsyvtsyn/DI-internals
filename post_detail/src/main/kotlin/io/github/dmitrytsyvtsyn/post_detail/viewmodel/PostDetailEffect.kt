package io.github.dmitrytsyvtsyn.post_detail.viewmodel

internal sealed interface PostDetailEffect {
    data object NavigateBack : PostDetailEffect
}