package io.github.dmitrytsyvtsyn.post_detail.viewmodel

import io.github.dmitrytsyvtsyn.core.model.PostModel
import io.github.dmitrytsyvtsyn.core.model.StringResource

internal data class PostDetailState(
    val title: String = "",
    val content: String = "",
    val postModel: PostModel = PostModel.Empty,
    val titleErrorResource: StringResource = StringResource.Empty,
    val contentErrorResource: StringResource = StringResource.Empty
)