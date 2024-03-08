package com.achmadss.domain.common

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlin.system.measureTimeMillis

suspend inline fun <RequestType, ResultType> networkBound(
    crossinline query: suspend () -> ResultType,
    crossinline queryResult: suspend (ResultType) -> Unit,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: () -> Boolean = { true },
) = flow {
    if (shouldFetch()) {
        emit(UseCaseResult.Loading)
        try {
            val time = measureTimeMillis {
                saveFetchResult(fetch())
            }
            Log.e("NetworkBound", "Completed in $time ms")
            val data = query()
            queryResult(data)
            emit(UseCaseResult.Success(data))

        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            emit(UseCaseResult.Error(throwable))
        }
    } else {
        val data = query()
        queryResult(data)
        emit(UseCaseResult.Success(data))
    }

}