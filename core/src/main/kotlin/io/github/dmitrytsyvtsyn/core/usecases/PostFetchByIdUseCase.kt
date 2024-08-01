package io.github.dmitrytsyvtsyn.core.usecases

import io.github.dmitrytsyvtsyn.core.data.PostDao
import io.github.dmitrytsyvtsyn.core.model.IdLong
import io.github.dmitrytsyvtsyn.core.model.PostModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostFetchByIdUseCase(private val dao: PostDao) {

    suspend fun execute(id: IdLong, dispatcher: CoroutineDispatcher = Dispatchers.Default) = withContext(dispatcher) {
        if (id.isEmpty) {
            PostModel.Empty
        } else {
            val entity = dao.fetch(id.value)
            entity.toModel()
        }
    }

}