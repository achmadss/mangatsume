package com.achmadss.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(parameters: P): Flow<UseCaseResult<R>> = await(parameters)
        .catch { throwable ->
            throwable.printStackTrace()
            emit(UseCaseResult.Error(throwable))
        }
        .flowOn(coroutineDispatcher)

    protected abstract suspend fun await(parameters: P): Flow<UseCaseResult<R>>
}
