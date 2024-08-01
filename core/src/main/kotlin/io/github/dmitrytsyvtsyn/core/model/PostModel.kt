package io.github.dmitrytsyvtsyn.core.model

import kotlinx.datetime.LocalDateTime

data class PostModel(
    val id: IdLong = IdLong.Empty,
    val title: String = "",
    val content: String = "",
    val dateTime: LocalDateTime = LocalDateTime(2001, 9, 4, 0, 0),
) {
    companion object {
        val Empty = PostModel()
    }
}