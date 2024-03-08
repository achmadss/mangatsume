package com.achmadss.data.common

sealed class APICallResult<out T> {
    data class Success<out Result>(val data: Result) : APICallResult<Result>()
    data class Error(val error: Throwable) : APICallResult<Nothing>()
}