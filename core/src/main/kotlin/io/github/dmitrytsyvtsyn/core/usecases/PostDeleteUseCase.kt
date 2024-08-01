package io.github.dmitrytsyvtsyn.core.usecases

import io.github.dmitrytsyvtsyn.core.data.PostDao
import io.github.dmitrytsyvtsyn.core.model.PostModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostDeleteUseCase(private val dao: PostDao) {

    suspend fun execute(model: PostModel, dispatcher: CoroutineDispatcher = Dispatchers.Default) {
        return withContext(dispatcher) {
            dao.delete(model.id.value)
        }
    }

}