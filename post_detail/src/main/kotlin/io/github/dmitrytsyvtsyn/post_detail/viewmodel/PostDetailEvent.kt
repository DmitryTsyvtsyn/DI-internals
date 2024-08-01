package io.github.dmitrytsyvtsyn.post_detail.viewmodel

import io.github.dmitrytsyvtsyn.core.model.IdLong

internal sealed interface PostDetailEvent {
    class ChangeTitle(val title: String) : PostDetailEvent
    class ChangeContent(val content: String) : PostDetailEvent
    class FetchById(val id: IdLong) : PostDetailEvent
    data object Save : PostDetailEvent
}