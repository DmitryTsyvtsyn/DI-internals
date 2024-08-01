package io.github.dmitrytsyvtsyn.core.usecases

import io.github.dmitrytsyvtsyn.core.data.PostDao
import io.github.dmitrytsyvtsyn.core.datetime.DatetimeExtensions
import io.github.dmitrytsyvtsyn.core.model.PostModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostInsertUseCase(private val dao: PostDao) {

    suspend fun execute(
        model: PostModel,
        dispatcher: CoroutineDispatcher = Dispatchers.Default
    ) = withContext(dispatcher) {
        if (model.id.isNotEmpty) {
            dao.insert(model.toDatabase())
        } else {
            val modelWithUpdatedDateTime = model.copy(dateTime = DatetimeExtensions.nowDateTime())
            dao.insert(modelWithUpdatedDateTime.toDatabase())
        }
    }

}