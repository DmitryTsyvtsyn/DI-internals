package io.github.dmitrytsyvtsyn.post_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.dmitrytsyvtsyn.core.di.DI
import io.github.dmitrytsyvtsyn.core.model.PostModel
import io.github.dmitrytsyvtsyn.core.model.StringResource
import io.github.dmitrytsyvtsyn.core.usecases.PostFetchByIdUseCase
import io.github.dmitrytsyvtsyn.core.usecases.PostInsertUseCase
import io.github.dmitrytsyvtsyn.post_detail.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class PostDetailViewModel : ViewModel() {

    private val insertUseCase : PostInsertUseCase = DI.instance()
    private val fetchByIdUseCase: PostFetchByIdUseCase = DI.instance()

    private val _state = MutableStateFlow(PostDetailState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PostDetailEffect>()
    val effect = _effect.asSharedFlow()

    fun handleEvent(event: PostDetailEvent) {
        when(event) {
            is PostDetailEvent.ChangeTitle -> handleEvent(event)
            is PostDetailEvent.ChangeContent -> handleEvent(event)
            is PostDetailEvent.FetchById -> handleEvent(event)
            is PostDetailEvent.Save -> handleEvent(event)
        }
    }

    private fun handleEvent(event: PostDetailEvent.ChangeTitle) {
        _state.value = _state.value.copy(
            title = event.title,
            titleErrorResource = StringResource.Empty
        )
    }

    private fun handleEvent(event: PostDetailEvent.ChangeContent) {
        _state.value = _state.value.copy(
            content = event.content,
            contentErrorResource = StringResource.Empty
        )
    }

    private fun handleEvent(event: PostDetailEvent.FetchById) = viewModelScope.launch {
        val postModel = fetchByIdUseCase.execute(event.id)
        if (postModel != PostModel.Empty) {
            _state.value = _state.value.copy(
                title = postModel.title,
                content = postModel.content,
                postModel = postModel
            )
        }
    }

    private fun handleEvent(event: PostDetailEvent.Save) {
        val state = _state.value

        if (state.title.isBlank()) {
            _state.value = state.copy(
                titleErrorResource = StringResource(R.string.title_is_empty)
            )

            return
        }

        if (state.content.isBlank()) {
            _state.value = state.copy(
                contentErrorResource = StringResource(R.string.content_is_empty)
            )

            return
        }

        viewModelScope.launch {
            insertUseCase.execute(
                PostModel(
                    id = state.postModel.id,
                    title = state.title,
                    content = state.content,
                    dateTime = state.postModel.dateTime
                )
            )
            _effect.emit(PostDetailEffect.NavigateBack)
        }
    }

}