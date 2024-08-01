package io.github.dmitrytsyvtsyn.core.usecases

import io.github.dmitrytsyvtsyn.core.data.PostEntity
import io.github.dmitrytsyvtsyn.core.datetime.toEpochMillis
import io.github.dmitrytsyvtsyn.core.datetime.toLocalDateTime
import io.github.dmitrytsyvtsyn.core.model.IdLong
import io.github.dmitrytsyvtsyn.core.model.PostModel
import java.util.UUID

fun PostModel.toDatabase(): PostEntity =
    if (id.isEmpty) {
        val id = UUID.randomUUID().mostSignificantBits
        val limitedId = id.and(Long.MAX_VALUE)
        PostEntity(
            id = limitedId,
            dateTime = dateTime.toEpochMillis(),
            title = title,
            content = content
        )
    } else {
        PostEntity(
            id = id.value,
            dateTime = dateTime.toEpochMillis(),
            title = title,
            content = content
        )
    }

fun PostEntity.toModel(): PostModel =
    PostModel(
        id = IdLong(id),
        dateTime = dateTime.toLocalDateTime(),
        title = title,
        content = content
    )