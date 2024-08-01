package io.github.dmitrytsyvtsyn.deps_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.dmitrytsyvtsyn.core.di.DI
import io.github.dmitrytsyvtsyn.core.model.PostModel
import io.github.dmitrytsyvtsyn.core.usecases.PostDeleteUseCase
import io.github.dmitrytsyvtsyn.core.usecases.PostFetchAllUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class PostListViewModel : ViewModel() {

    private val fetchAllUseCase: PostFetchAllUseCase = DI.instance()
    private val fetchDeleteUseCase: PostDeleteUseCase = DI.instance()

    private val _state = MutableStateFlow(persistentListOf<PostModel>())
    val state = _state

    private val _effect = MutableSharedFlow<PostListEffect>()
    val effect = _effect

    fun handleEvent(event: PostListEvent) {
        when(event) {
            is PostListEvent.FetchAll -> handleEvent(event)
            is PostListEvent.Delete -> handleEvent(event)
            is PostListEvent.View -> handleEvent(event)
            is PostListEvent.Add -> handleEvent(event)
        }
    }

    private fun handleEvent(event: PostListEvent.FetchAll) = viewModelScope.launch {
        _state.value = fetchAllUseCase.execute().toPersistentList()
    }

    private fun handleEvent(event: PostListEvent.Delete) = viewModelScope.launch {
        fetchDeleteUseCase.execute(event.model)
        handleEvent(PostListEvent.FetchAll)
    }

    private fun handleEvent(event: PostListEvent.View) = viewModelScope.launch {
        _effect.emit(PostListEffect.View(event.model))
    }

    private fun handleEvent(event: PostListEvent.Add) = viewModelScope.launch {
        _effect.emit(PostListEffect.Add)
    }

}