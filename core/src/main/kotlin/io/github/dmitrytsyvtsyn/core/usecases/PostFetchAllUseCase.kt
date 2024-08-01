package io.github.dmitrytsyvtsyn.core.usecases

import io.github.dmitrytsyvtsyn.core.data.PostDao
import io.github.dmitrytsyvtsyn.core.model.PostModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostFetchAllUseCase(private val dao: PostDao) {

    suspend fun execute(dispatcher: CoroutineDispatcher = Dispatchers.Default): List<PostModel> {
        return withContext(dispatcher) {
            val entities = dao.fetch()
            entities.map { entity -> entity.toModel() }
        }
    }

}