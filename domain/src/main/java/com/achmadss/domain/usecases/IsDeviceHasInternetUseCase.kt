package com.achmadss.domain.usecases

import com.achmadss.domain.common.FlowUseCase
import com.achmadss.domain.common.NetworkUtils
import com.achmadss.domain.common.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsDeviceHasInternetUseCase @Inject constructor(
    private val networkUtils: NetworkUtils,
): FlowUseCase<Unit, Boolean>() {
    override suspend fun await(parameters: Unit): Flow<UseCaseResult<Boolean>> = flow {
        emit(UseCaseResult.Success(networkUtils.isConnectedToInternet()))
    }
}